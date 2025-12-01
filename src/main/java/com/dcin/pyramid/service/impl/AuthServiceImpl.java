package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.exception.UserAlreadyRegisteredException;
import com.dcin.pyramid.model.dto.auth.JwtResponse;
import com.dcin.pyramid.model.dto.auth.LoginRequest;
import com.dcin.pyramid.model.dto.auth.PlayerSignUpRequest;
import com.dcin.pyramid.model.dto.auth.StoreSignUpRequest;
import com.dcin.pyramid.model.entity.Address;
import com.dcin.pyramid.model.entity.Player;
import com.dcin.pyramid.model.entity.Store;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.PlayerRepository;
import com.dcin.pyramid.repository.StoreRepository;
import com.dcin.pyramid.security.JwtProvider;
import com.dcin.pyramid.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final PlayerRepository playerRepository;
    private final StoreRepository storeRepository;

    @Override
    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        User user = (User) authentication.getPrincipal();
        String token = jwtProvider.generateToken(user);
        return new JwtResponse(token);
    }

    @Override
    public JwtResponse playerSignup(PlayerSignUpRequest request) {
        if (playerRepository.existsByEmail(request.email())) {
            throw new UserAlreadyRegisteredException();
        }
        if (playerRepository.existsByNickname(request.nickname())) {
            throw new UserAlreadyRegisteredException("Nickname not available");
        }
        Player player = Player.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .build();
        playerRepository.save(player);
        String token = jwtProvider.generateToken(player);
        return new JwtResponse(token);
    }

    @Override
    public JwtResponse storeSignup(StoreSignUpRequest request) {
        if (storeRepository.existsByEmail(request.email())) {
            throw new UserAlreadyRegisteredException();
        }
        if (storeRepository.existsByNickname(request.nickname())) {
            throw new UserAlreadyRegisteredException("Store name not available");
        }
        Store store = Store.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .address(Address.builder()
                        .country(request.country())
                        .city(request.city())
                        .googleMapsLink(request.googleMapsLink())
                        .build())
                .storeCapacity(request.storeCapacity())
                .cardMarketLink(request.cardMarketLink())
                .build();

        storeRepository.save(store);
        String token = jwtProvider.generateToken(store);
        return new JwtResponse(token);
    }
}
