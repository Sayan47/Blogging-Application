package com.sayan.blog.app.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sayan.blog.app.entities.User;

public interface UserRepo extends JpaRepository<User, String> {

	Optional<User> findByEmail(String email);

}
