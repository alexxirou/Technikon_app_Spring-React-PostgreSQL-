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
    private final Collection<SimpleGrantedAuthority> authorities;
    private final boolean accountNonExpired = true;
    private final boolean accountNonLocked = true;
    private final boolean credentialsNonExpired = true;
    private final boolean enabled = true;

        public UserInfoDetails(User user)  {
            username = user.getTin();
            password = user.getPassword();

            // Determine authority based on the type of user
            if (user instanceof Admin) {
                authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_USER"));
            } else {
                authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
            }
        }

        @Override
        public Collection<SimpleGrantedAuthority> getAuthorities() {
            return authorities;
        }
}

