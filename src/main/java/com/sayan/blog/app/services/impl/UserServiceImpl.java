package com.sayan.blog.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sayan.blog.app.entities.User;
import com.sayan.blog.app.exceptions.ResourceNotFoundException;
import com.sayan.blog.app.payloads.UserDto;
import com.sayan.blog.app.repos.UserRepo;
import com.sayan.blog.app.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = UserDtoToUser(userDto);
		user.setPassword(encoder.encode(user.getPassword()));
		User savedUser = userRepo.save(user);
//		System.out.println(user);
		return UserToUserDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(encoder.encode(userDto.getPassword()));
		user.setAbout(userDto.getAbout());
		user.setRoles(UserDtoToUser(userDto).getRoles());

		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.UserToUserDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(String userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		System.out.println(user);
		return UserToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepo.findAll();
		System.out.println(users);
		List<UserDto> userDtoList = users.stream().map((user) -> UserToUserDto(user)).collect(Collectors.toList());
		return userDtoList;
	}

	@Override
	public void deleteUser(String userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		userRepo.delete(user);

	}

	public User UserDtoToUser(UserDto userDto) {
		return modelMapper.map(userDto, User.class);
	}

	public UserDto UserToUserDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}

}
