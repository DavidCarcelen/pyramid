package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.auth.JwtResponse;
import com.dcin.pyramid.model.dto.auth.LoginRequest;
import com.dcin.pyramid.model.dto.auth.PlayerSignUpRequest;
import com.dcin.pyramid.model.dto.auth.StoreSignUpRequest;
import com.dcin.pyramid.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pyramid/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    //one endpoint missing before signup to choose store or player

    @PostMapping("/signup/player")
    public ResponseEntity<JwtResponse> playerSignup (@Valid @RequestBody PlayerSignUpRequest request){
        return ResponseEntity.ok(authService.playerSignup(request));
    }
    @PostMapping("/signup/store")
    public ResponseEntity<JwtResponse> storeSignup (@Valid @RequestBody StoreSignUpRequest request){
        return ResponseEntity.ok(authService.storeSignup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login (@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
}
