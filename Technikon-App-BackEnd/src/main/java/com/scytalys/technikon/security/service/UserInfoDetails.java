package com.scytalys.technikon.security.service;


import com.scytalys.technikon.domain.Admin;
import com.scytalys.technikon.domain.User;
import lombok.Getter;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;



@Getter
public class UserInfoDetails implements UserDetails {
    private final String username;
    private final String password;
    private final long id;
    private final String tin;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean accountNonExpired = true;
    private final boolean accountNonLocked = true;
    private final boolean credentialsNonExpired = true;
    private final boolean enabled = true;

    public UserInfoDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.id =user.getId();
        this.tin = user.getTin();

        // Determine authority based on the type of user
        if (user instanceof Admin) {
            this.authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            this.authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }
}