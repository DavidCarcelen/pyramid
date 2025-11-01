package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.auth.JwtResponse;
import com.dcin.pyramid.model.dto.auth.LoginRequest;
import com.dcin.pyramid.model.dto.auth.PlayerSignUpRequest;
import com.dcin.pyramid.model.dto.auth.StoreSignUpRequest;

public interface AuthService {
    JwtResponse login (LoginRequest request);
    JwtResponse playerSignup(PlayerSignUpRequest request);
    JwtResponse storeSignup(StoreSignUpRequest request);

}
