package com.sayan.blog.app.services;

import java.util.List;

import com.sayan.blog.app.payloads.PostDto;
import com.sayan.blog.app.payloads.PostResponse;

public interface PostService {

	// create

	PostDto createPost(PostDto postDto, String userId, String categoryId);

	// update

	PostDto updatePost(PostDto postDto, String postId);

	// delete

	void deletePost(String postId);

	// get all posts

	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	// get single post

	PostDto getPostById(String postId);

	// get all posts by category

	List<PostDto> getPostsByCategory(String categoryId);

	// get all posts by user
	List<PostDto> getPostsByUser(String userId);

	// search posts
	List<PostDto> searchPosts(String keyword);

}
