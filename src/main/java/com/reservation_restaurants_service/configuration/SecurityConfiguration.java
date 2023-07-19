package com.reservation_restaurants_service.configuration;

import com.reservation_restaurants_service.configuration.jwt.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableJpaAuditing
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtConfig jwtConfig;

    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };


    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/user/*").hasAnyRole("USER", "ADMIN", "MANAGER")
                .antMatchers("/reservation/restaurant/*").hasAnyRole("USER", "ADMIN")
                .antMatchers("/reservation/status/*").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/restaurant/*").hasAnyRole("ADMIN", "USER")
                .antMatchers("/registration", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(jwtConfig);
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/v3/api-docs/**",
                "/swagger-ui/**");
    }
}