package com.dcin.pyramid.security;

import com.dcin.pyramid.exception.EntityNotFoundException;
import com.dcin.pyramid.repository.PlayerRepository;
import com.dcin.pyramid.repository.StoreRepository;
import com.dcin.pyramid.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PlayerRepository playerRepository;
    private final StoreRepository storeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return playerRepository.findByEmail(email)
                .map(player -> (UserDetails) player)
                .or(() -> storeRepository.findByEmail(email)
                        .map(store -> (UserDetails) store))
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + email));
    }
}

