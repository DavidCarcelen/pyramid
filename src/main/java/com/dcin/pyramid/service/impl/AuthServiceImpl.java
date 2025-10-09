package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.exception.UserAlreadyRegisteredException;
import com.dcin.pyramid.model.dto.JwtResponse;
import com.dcin.pyramid.model.dto.LoginRequest;
import com.dcin.pyramid.model.dto.SignUpRequest;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        User user = (User) authentication.getPrincipal();
        String token = jwtProvider.generateToken(user);
        return new JwtResponse(token);
    }

    @Override
    public JwtResponse signup(SignUpRequest request) {
        userRepository.findByEmail(request.email())
                .ifPresent(user -> {
                    throw new UserAlreadyRegisteredException("Email already registered.");
                });
        userRepository.findByNickname(request.nickname())
                .ifPresent(user -> {
                    throw new UserAlreadyRegisteredException("Nickname not available.");
                });
            User user = User.builder()
                    .email(request.email())
                    .password(passwordEncoder.encode(request.password()))
                    .nickname(request.nickname())
                    .role(request.role())
                    .build();
            userRepository.save(user);
            String token = jwtProvider.generateToken(user);
        return new JwtResponse(token);
    }
}
