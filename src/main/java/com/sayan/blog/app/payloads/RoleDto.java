package com.sayan.blog.app.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleDto {
	
	@NotEmpty
	private String roleId;
	@NotEmpty
	@Size(max=50, message = "Role can have a maximum of 50 characters!!")
	private String name;

}
