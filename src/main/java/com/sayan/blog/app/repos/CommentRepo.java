package com.sayan.blog.app.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sayan.blog.app.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, String> {

}
