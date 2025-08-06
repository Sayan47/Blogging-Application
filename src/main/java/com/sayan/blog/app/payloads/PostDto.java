package com.sayan.blog.app.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDto {

	@NotBlank
	private String postId;

	@NotBlank
	@Size(max = 100, message = "Post title cannot have more than 100 characters!!")
	private String title;

	@NotBlank
	@Size(max = 1000000000, message = "Post content cannot have more than 1000000000 characters!!")
	private String content;

	private String imageName;

	private Date addedDate;

	private UserDto user;

	private CategoryDto category;

	private Set<CommentDto> comments = new HashSet<>();

}
