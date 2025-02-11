package com.mycompany.ecommerce_microservices.usermanagementservice.services;

import com.mycompany.ecommerce_microservices.usermanagementservice.models.User;
import com.mycompany.ecommerce_microservices.usermanagementservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String roles) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roles));
    }
}
