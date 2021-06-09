package com.po.trucking.user.security;

import com.po.trucking.user.dto.UserDtoWithPassword;
import com.po.trucking.user.model.Role;
import com.po.trucking.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        log.info("start creating user");
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getCarrierId(),
                mapToGrantedAuthorities(user.getRole())
        );
    }


    private static List<GrantedAuthority> mapToGrantedAuthorities(Role userRole) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userRole.getValue()));
        return authorities;
    }
}

