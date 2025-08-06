package com.sayan.blog.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sayan.blog.app.payloads.ApiResponse;
import com.sayan.blog.app.payloads.CommentDto;
import com.sayan.blog.app.services.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto comment, @PathVariable String postId) {

		CommentDto createComment = commentService.createComment(comment, postId);
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable String commentId) {

		commentService.deleteComment(commentId);

		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully !!", true), HttpStatus.OK);
	}

}
