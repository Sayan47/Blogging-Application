package com.sayan.blog.app.payloads;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

	@NotEmpty
	private String userId;
	@NotEmpty
	@Size(max = 100, message = "User name can have maximum of 100 characters!!")
	private String name;
	@NotEmpty
	@Email(message = "Email is not valid!!")
	private String email;
	@NotEmpty
	@Size(min = 3, max = 10, message = "Password must be min of 3 chars and max of 10 chars !!")
	private String password;
	@NotEmpty
	private String about;

	private Set<RoleDto> roles = new HashSet<>();

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

}
