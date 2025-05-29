package com.example.nobsv2.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    private final CustomUserRepository customUserRepository;

    public CustomUserDetailsService(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //can add logic to deal with the optional
        CustomUser customUser = customUserRepository.findByUsername(username).get();

        //this is where you can add roles and authorities to the user (idk how)

        //perhaps those are in the same table or they are in a separate table so we would have to use
        //relational mappings

        return User
                .withUsername(customUser.getUsername())
                .password(customUser.getPassword())
                .build();
    }
}
