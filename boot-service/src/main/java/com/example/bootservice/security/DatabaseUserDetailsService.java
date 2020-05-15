package com.example.bootservice.security;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class DatabaseUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
	private static final Logger log = LoggerFactory.getLogger(DatabaseUserDetailsService.class);

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		JwtPrincipal principal = (JwtPrincipal) token.getPrincipal();

		String username = principal.getUsername();
		if (StringUtils.isBlank(username)) {
			throw new UsernameNotFoundException("No username provided");
		}

		log.debug("Lookup username {} in database", username);
		//in mod normal
//		SecurityUser securityUser = userRepository.findByUsername(username);
		SecurityUser securityUser = new SecurityUser(username, username.toUpperCase(), "USER","??");

		log.debug("UserVO found in database");
		if (!securityUser.isEnabled()) {
			throw new UsernameNotFoundException("UserVO is inactive in the database");
		}
		securityUser.setCountry(principal.getCountry());

		log.debug("Successful login");
		return securityUser;
	}


}
