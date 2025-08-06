package com.sayan.blog.app.services;

import com.sayan.blog.app.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, String postId);

	void deleteComment(String commentId);

}
