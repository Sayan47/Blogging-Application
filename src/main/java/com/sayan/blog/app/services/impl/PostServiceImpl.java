package com.sayan.blog.app.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sayan.blog.app.entities.Category;
import com.sayan.blog.app.entities.Post;
import com.sayan.blog.app.entities.User;
import com.sayan.blog.app.exceptions.ResourceNotFoundException;
import com.sayan.blog.app.payloads.PostDto;
import com.sayan.blog.app.payloads.PostResponse;
import com.sayan.blog.app.repos.CategoryRepo;
import com.sayan.blog.app.repos.PostRepo;
import com.sayan.blog.app.repos.UserRepo;
import com.sayan.blog.app.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, String userId, String categoryId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "User id", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id ", categoryId));

		Post post = postDtoToPost(postDto);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = postRepo.save(post);

		return postToPostDto(newPost);
	}

	@Override
	public PostDto updatePost(PostDto postDto, String postId) {

		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		post.setImageName(postDto.getImageName());
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());

		Post updatedPost = postRepo.save(post);

		PostDto updatedPostDto = postToPostDto(updatedPost);

		return updatedPostDto;
	}

	@Override
	public void deletePost(String postId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		postRepo.delete(post);
	}

	@Override
	public PostDto getPostById(String postId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		PostDto postDto = postToPostDto(post);

		return postDto;
	}

	@Override
	public List<PostDto> getPostsByCategory(String categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id ", categoryId));
		List<Post> posts = postRepo.findByCategory(category);
		List<PostDto> postDtoList = posts.stream().map((post) -> postToPostDto(post)).collect(Collectors.toList());

		return postDtoList;
	}

	@Override
	public List<PostDto> getPostsByUser(String userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "User id", userId));
		List<Post> posts = postRepo.findByUser(user);
		List<PostDto> postDtoList = posts.stream().map((post) -> postToPostDto(post)).collect(Collectors.toList());

		return postDtoList;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = postRepo.searchByTitle("%" + keyword + "%");
		List<PostDto> postDtoList = posts.stream().map((post) -> postToPostDto(post)).collect(Collectors.toList());

		return postDtoList;
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> page = postRepo.findAll(p);
		List<Post> posts = page.getContent();
		List<PostDto> postDtoList = posts.stream().map((post) -> postToPostDto(post)).collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtoList);
		postResponse.setPageNumber(page.getNumber());
		postResponse.setPageSize(page.getSize());
		postResponse.setTotalElements(page.getTotalElements());
		postResponse.setTotalPages(page.getTotalPages());
		postResponse.setLastPage(page.isLast());

		return postResponse;
	}

	public PostDto postToPostDto(Post post) {
		return modelMapper.map(post, PostDto.class);
	}

	public Post postDtoToPost(PostDto postDto) {
		return modelMapper.map(postDto, Post.class);
	}

}
