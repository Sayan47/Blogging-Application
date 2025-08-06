package com.sayan.blog.app.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sayan.blog.app.entities.Comment;
import com.sayan.blog.app.entities.Post;
import com.sayan.blog.app.exceptions.ResourceNotFoundException;
import com.sayan.blog.app.payloads.CommentDto;
import com.sayan.blog.app.repos.CommentRepo;
import com.sayan.blog.app.repos.PostRepo;
import com.sayan.blog.app.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, String postId) {

		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		Comment comment = commentDtoToComment(commentDto);
		comment.setPost(post);
		Comment newComment = commentRepo.save(comment);

		return commentToCommentDto(newComment);
	}

	@Override
	public void deleteComment(String commentId) {

		Comment comment = commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id: ", commentId));
		System.out.println(comment.getCommentId());
		commentRepo.delete(comment);

	}

	public Comment commentDtoToComment(CommentDto commentDto) {
		return modelMapper.map(commentDto, Comment.class);
	}

	public CommentDto commentToCommentDto(Comment comment) {
		return modelMapper.map(comment, CommentDto.class);
	}

}
