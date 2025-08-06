package com.sayan.blog.app.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDto {

	@NotBlank
	private String commentId;
	@NotBlank
	@Size(max = 1000, message = "The comments can not have more than 1000 characters!!")
	private String content;

}
