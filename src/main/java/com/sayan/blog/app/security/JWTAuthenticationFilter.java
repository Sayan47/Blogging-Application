package com.sayan.blog.app.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTHelper helper;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain cc)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		logger.info("Auth Header : {}", authHeader);
		if (authHeader != null && authHeader.startsWith("Bearer")) {

			String userName = null;
			String token = authHeader.substring(7);
			logger.info("Token : {}", token);
			try {
				userName = helper.getUserNameFromToken(token);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (ExpiredJwtException e) {
				e.printStackTrace();
			} catch (MalformedJwtException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
				Boolean tokenValidated = helper.validateToken(token, userDetails);
				if (tokenValidated) {

					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);

				} else {
					logger.info("Token is not valid!!");
				}

			} else {
				logger.info("The username is either empty or the SecurityContextHolder is not null !!");
			}

		} else {
			logger.info("The header is Invalid!! Please check the header and try again!!");
		}

		cc.doFilter(request, response);
	}

}
