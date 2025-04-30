package com.seikan.store.mappers;

import com.seikan.store.dtos.get.UserDto;
import com.seikan.store.dtos.post.RegisterUserRequest;
import com.seikan.store.dtos.put.UpdateUserRequest;
import com.seikan.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request,@MappingTarget User user);
}
