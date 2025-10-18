package com.dcin.pyramid.security;

import com.dcin.pyramid.exception.EntityNotFoundException;
import com.dcin.pyramid.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws EntityNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException("User not found with email: " + email));
    }
}
