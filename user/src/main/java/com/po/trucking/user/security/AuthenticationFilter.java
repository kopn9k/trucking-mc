package com.po.trucking.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.po.trucking.user.dto.AuthenticationUserDto;
import com.po.trucking.user.dto.UserDto;
import com.po.trucking.user.dto.UserDtoWithPassword;
import com.po.trucking.user.model.Role;
import com.po.trucking.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    UserService userService;
    Environment environment;

    public AuthenticationFilter(UserService userService, Environment environment, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationUserDto creds = new ObjectMapper().readValue(request.getInputStream(), AuthenticationUserDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        JwtUser jwtUser = ((JwtUser) authResult.getPrincipal());
        String userName = jwtUser.getUsername();
        Claims claims = Jwts.claims().setSubject(userName);
        List<String> roles = new ArrayList<>();
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) jwtUser.getAuthorities();
        String role = authorities.get(0).getAuthority();
        Long carrierId = jwtUser.getCarrierId();
        roles.add(role);
        claims.put("roles", roles);
        claims.put("company", carrierId);

        Date now = new Date(System.currentTimeMillis());
        long validityInMilliseconds = Long.parseLong(environment.getProperty("token.expiration_time"));
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();

        response.addHeader("token", token);
        response.addHeader("carrierId", carrierId.toString());
    }
}