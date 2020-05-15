package com.example.bootservice.security;

import java.io.Serializable;

public class JwtPrincipal implements Serializable {
	private final String username;
	private final String country;

	public JwtPrincipal(String username, String country) {
		this.username = username;
		this.country = country;
	}

	public String getUsername() {
		return username;
	}

	public String getCountry() {
		return country;
	}
}
