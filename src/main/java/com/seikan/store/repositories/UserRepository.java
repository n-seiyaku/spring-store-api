package com.seikan.store.repositories;

import com.seikan.store.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "profile")
    @Query("SELECT u from User u")
    List<User> findAllWithProfile(Sort sort);

    boolean existsByEmailIgnoreCase(String email);
}

