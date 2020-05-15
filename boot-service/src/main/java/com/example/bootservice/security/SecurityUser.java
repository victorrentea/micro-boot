package com.example.bootservice.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static java.util.Arrays.asList;

public class SecurityUser implements UserDetails {
    private final String username;
    private final String fullName;
    private final String role;
    private String country; // dynamic, based on login request

    public SecurityUser(String username, String fullName, String role, String country) {
        this.username = username;
        this.fullName = fullName;
        this.role = role;
        this.country = country;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return asList(() -> role);
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public String getCountry() {
        return country;
    }

    public SecurityUser setCountry(String country) {
        this.country = country;
        return this;
    }
}
