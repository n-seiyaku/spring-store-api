package com.seikan.store.controllers;

import com.seikan.store.dtos.get.UserDto;
import com.seikan.store.dtos.post.ChangePasswordRequest;
import com.seikan.store.dtos.post.RegisterUserRequest;
import com.seikan.store.dtos.put.UpdateUserRequest;
import com.seikan.store.mappers.UserMapper;
import com.seikan.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort
    ) {
        if(!Set.of("name", "email").contains(sort))
            sort = "id";

        var users = userRepository.findAllWithProfile(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        var user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        boolean emailExists = userRepository.existsByEmailIgnoreCase(request.getEmail());
        if(emailExists) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Email has been used.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        var password = request.getPassword();
        // real check
        // var passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&=(){}])[A-Za-z\\d@$!%*?&=(){}]{8,20}$");
        var passwordPattern = Pattern.compile("^(123456|(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&=(){}])[A-Za-z\\d@$!%*?&=(){}\\[\\]]{8,20})$");
        if(!passwordPattern.matcher(password).matches()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Password is incorrect.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        var user = userMapper.toEntity(request);
        userRepository.save(user);

        var userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserRequest request
    ) {
        var user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        userMapper.update(request, user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User has been deleted.");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        if(!user.getPassword().equals(request.getOldPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password has been changed.");

        return ResponseEntity.ok(response);
    }
}
