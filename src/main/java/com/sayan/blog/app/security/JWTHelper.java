package com.sayan.blog.app.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTHelper {

	// Token validity in seconds
	private final static long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	private final static String TEST_STRING = "DEMO STRING";
	private final static String TEST_STRING_NEW = "NEW DEMO STRING";
	private String secret = "Testadafssdfasasfasfsadfsdjfhsafdhsafdthsafthfdgsafdashdfsahfdjsafdhjasfhdfasjfdhjasfdjahsfdsahjfdjsafdjashfdhjasfdjyasfdjysadgjyadadhgaffhgdfafdhafdfahfdhafhgfdghafdhafdjfafdgafdghsfgffdshgfdahafdhaffdsfhsfdhafhfafhfdaghffdahfgfsdfhsffahfafdhfsdhafdgh";
	private SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

	public String getUserNameFromToken(String token) {

		return getClaimsFromToken(token, t -> t.getSubject());
	}

	public Date getExpirationFromToken(String token) {

		return getClaimsFromToken(token, e -> e.getExpiration());
	}

	private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().claims(claims).subject(subject).issuedAt(new Date(System.currentTimeMillis())

		)
				.expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)).signWith(secretKey)
				.compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {

		return (isTokenValid(token) && userDetails.getUsername().equals(getUserNameFromToken(token)));

	}

	private boolean isTokenValid(String token) {
		Date expirationFromToken = getExpirationFromToken(token);
		return expirationFromToken.after(new Date());
	}

}
