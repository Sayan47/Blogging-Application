package com.sayan.blog.app.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sayan.blog.app.entities.Role;

public interface RoleRepo extends JpaRepository<Role, String> {

}
