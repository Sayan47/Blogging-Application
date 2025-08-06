package com.sayan.blog.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sayan.blog.app.payloads.ApiResponse;
import com.sayan.blog.app.payloads.UserDto;
import com.sayan.blog.app.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdUser = userService.createUser(userDto);
		return new ResponseEntity<UserDto>(createdUser, HttpStatus.CREATED);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> upadateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
		UserDto updatedUser = userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
		UserDto userDto = userService.getUserById(userId);
		return ResponseEntity.ok(userDto);
	}

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getUsers() {
		List<UserDto> userDtoList = userService.getAllUsers();
		return ResponseEntity.ok(userDtoList);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") String uid) {
		userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
	}

}
