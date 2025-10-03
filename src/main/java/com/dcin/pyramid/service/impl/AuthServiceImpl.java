package com.dcin.pyramid.service.impl;

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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
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
        return null;
    }
}
