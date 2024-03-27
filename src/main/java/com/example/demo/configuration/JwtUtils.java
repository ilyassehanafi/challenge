package com.example.demo.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class JwtUtils {
	private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();
	private static final String ISSUER = "coding_streams_auth_server";

	private static final String BEARER = "Bearer ";
	private JwtUtils() {
	}

	public static boolean validateToken(String jwtToken) {
		return parseToken(jwtToken).isPresent();
	}

	private static Optional<Claims> parseToken(String jwtToken) {
		var jwtParser = Jwts.parser()
				.verifyWith(secretKey)
				.build();

		try {
			return Optional.of(jwtParser.parseSignedClaims(jwtToken)
					.getPayload());
		} catch (JwtException | IllegalArgumentException e) {
			log.error("JWT Exception occurred");
		}

		return Optional.empty();
	}

	public static Optional<String> getUsernameFromToken(String jwtToken) {
		var claimsOptional = parseToken(jwtToken);
		return claimsOptional.map(Claims::getSubject);
	}

	public static String generateToken(String email) {

		var currentDate = new Date();
		var jwtExpirationInMinutes = 10;

		var expiration = DateUtils.addMinutes(currentDate, jwtExpirationInMinutes);
		return BEARER + Jwts.builder()
				.id(UUID.randomUUID().toString())
				.issuer(ISSUER)
				.subject(email)
				.signWith(secretKey)
				.issuedAt(currentDate)
				.expiration(expiration)
				.compact();
	}
	public static Optional<String> getTokenFromRequest(HttpServletRequest request) {
		// Extract authentication header
		var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		// Bearer <JWT TOKEN>
		if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
			return Optional.of(authHeader.substring(7));
		}

		return Optional.empty();
	}
}
