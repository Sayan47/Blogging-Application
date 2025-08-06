package com.sayan.blog.app.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDto {

	@NotBlank
	private String categoryId;
	@NotBlank
	@Size(max = 100, message = "The category title cannot be more than 100 characters!!!")
	private String categoryTitle;
	@NotBlank
	@Size(max = 500, message = "The category description cannot be more than 500 characters!!!")
	private String categoryDescription;

}
