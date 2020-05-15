package com.example.bootservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

public class JwtAuthorizationHeaderFilter extends AbstractPreAuthenticatedProcessingFilter {
	private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationHeaderFilter.class);
	@Value("${jwt.secret}")
	private String jwtSecret;

	public JwtAuthorizationHeaderFilter(AuthenticationManager authenticationManager) {
		setAuthenticationManager(authenticationManager);
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String jwtToken = request.getHeader("JWT-Header");
        if (jwtToken == null) {
			log.warn("Header {} not set", "JWT-Header");
            return null;
        }

        log.debug("Received Header: " + jwtToken);
		log.debug("Hint: Try to decode it on http://jwt.io/");

        try {
			Claims claims = Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary("secret"))
					.parseClaimsJws(jwtToken)
					.getBody();

			String country = (String) claims.get("country");
			log.info("Attempting login with user={} and country={}", claims.getSubject(), country);
			return new JwtPrincipal(claims.getSubject(), country);
		} catch (UnsupportedJwtException jwtException) {
			throw new PreAuthenticatedCredentialsNotFoundException("Invalid JWT Token", jwtException);
		}
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "N/A";
	}
}