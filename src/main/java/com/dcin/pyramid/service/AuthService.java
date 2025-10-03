package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.JwtResponse;
import com.dcin.pyramid.model.dto.LoginRequest;
import com.dcin.pyramid.model.dto.SignUpRequest;

public interface AuthService {
    JwtResponse login (LoginRequest request);
    JwtResponse signup(SignUpRequest request);

}
