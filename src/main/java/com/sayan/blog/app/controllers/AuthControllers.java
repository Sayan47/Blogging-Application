package com.sayan.blog.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sayan.blog.app.payloads.JWTRequest;
import com.sayan.blog.app.payloads.JWTResponse;
import com.sayan.blog.app.security.CustomUserDetailsService;
import com.sayan.blog.app.security.JWTHelper;

@RestController
@RequestMapping("/auth")
public class AuthControllers {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JWTHelper helper;

	@PostMapping("/login")
	public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request) {
		doAuthenticate(request.getEmail(), request.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		String token = helper.generateToken(userDetails);
		System.out.println("Token : " + token);

		JWTResponse response = JWTResponse.builder().token(token).username(userDetails.getUsername()).build();

		return new ResponseEntity<JWTResponse>(response, HttpStatus.OK);

	}

	private void doAuthenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
				password);

		try {
			manager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid Username Password!!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
