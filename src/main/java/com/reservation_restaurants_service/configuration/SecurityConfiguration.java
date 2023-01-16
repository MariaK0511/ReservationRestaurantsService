package com.reservation_restaurants_service.configuration;

import com.reservation_restaurants_service.configuration.jwt.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/*").hasAnyRole("USER", "ADMIN", "MANAGER")
                .antMatchers("/reservation/restaurant/*").hasAnyRole("USER","ADMIN")
                .antMatchers("/reservation/status/*").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/restaurant/*").hasAnyRole("ADMIN", "USER")
                .antMatchers("/registration", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(jwtConfig);
    }
}




