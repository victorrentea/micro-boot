package com.example.bootservice;

import com.example.bootservice.security.DatabaseUserDetailsService;
import com.example.bootservice.security.JwtAuthorizationHeaderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .anyRequest().authenticated()
                .and()

                .authenticationProvider(preAuthenticatedProvider())
                .addFilterBefore(jwtFilter(), BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Bean
    public AuthenticationProvider preAuthenticatedProvider() {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(preauthUserDetailsService());
        return provider;
    }


    @Bean
    protected DatabaseUserDetailsService preauthUserDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    public JwtAuthorizationHeaderFilter jwtFilter() throws Exception {
        return new JwtAuthorizationHeaderFilter(authenticationManagerBean());
    }

}
