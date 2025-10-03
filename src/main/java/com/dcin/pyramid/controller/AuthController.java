package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.JwtResponse;
import com.dcin.pyramid.model.dto.LoginRequest;
import com.dcin.pyramid.model.dto.SignUpRequest;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.UserRepository;
import com.dcin.pyramid.security.JwtProvider;
import com.dcin.pyramid.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthService authService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignUpRequest request){
        String message;
        if (userRepository.existsByEmail(request.email())){
            message = "Email already in use";
        } else {
            User user = new User();
            user.setEmail(request.email());
            user.setPassword(passwordEncoder.encode(request.password()));
            user.setNickname(request.nickname());
            user.setRole(request.role());
            userRepository.save(user);// deberia estar en service?
            message = "User registered successfully";
        }
        return message;
    }
    /*@PostMapping("/login")
    public JwtResponse login (@RequestBody LoginRequest request){
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(()-> new UsernameNotFoundException("Email unregistered."));// esto enservice
        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        String token = jwtProvider.generateToken(user.getEmail(), user.getRole());
        return new JwtResponse(token);
    }*/
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login (@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
}
