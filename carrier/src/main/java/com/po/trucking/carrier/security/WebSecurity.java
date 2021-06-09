package com.po.trucking.carrier.security;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    Environment environment;


    public WebSecurity(Environment environment) {
        this.environment = environment;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .antMatchers("/api/v1/carriers/**").access("hasIpAddress(\"192.168.0.0/16\") or hasIpAddress(\"127.0.0.0/16\")")
                .anyRequest().authenticated()
                .and()
                .addFilter(new AuthorizationFilter(authenticationManager(), environment));
        http.headers().frameOptions().disable();
    }


}