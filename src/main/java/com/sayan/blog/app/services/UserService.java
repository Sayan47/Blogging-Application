package com.sayan.blog.app.services;

import java.util.List;

import com.sayan.blog.app.payloads.UserDto;

public interface UserService {

	UserDto createUser(UserDto userDto);

	UserDto updateUser(UserDto userDto, String userId);

	UserDto getUserById(String userId);

	List<UserDto> getAllUsers();

	void deleteUser(String userId);

}
