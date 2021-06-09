package com.po.trucking.user.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {

    private final long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String password;
    private final Long carrierId;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(long id, String email, String firstName, String lastName, String password, Long carrierId, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.authorities = authorities;
        this.carrierId = carrierId;
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    @JsonIgnore
    public Long getCarrierId() {
        return carrierId;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
