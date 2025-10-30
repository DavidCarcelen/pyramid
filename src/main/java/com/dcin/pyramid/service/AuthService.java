package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.auth.JwtResponse;
import com.dcin.pyramid.model.dto.auth.LoginRequest;
import com.dcin.pyramid.model.dto.auth.SignUpRequest;

public interface AuthService {
    JwtResponse login (LoginRequest request);
    JwtResponse signup(SignUpRequest request);

}
