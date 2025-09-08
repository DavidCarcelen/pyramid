package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.JwtResponse;
import com.dcin.pyramid.model.dto.LoginRequest;
import com.dcin.pyramid.model.dto.SignUpRequest;
import com.dcin.pyramid.model.entity.Player;
import com.dcin.pyramid.model.entity.Store;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.UserRepository;
import com.dcin.pyramid.security.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }
    @PostMapping("/signup")
    public String signup(@RequestBody SignUpRequest request){   //FEO FEO
        if (userRepository.existsByEmail(request.email())){
            return "Email already in use";
        }
        if ("PLAYER".equalsIgnoreCase(request.role())){ //limpiar
            Player player = new Player();
            player.setEmail(request.email());
            player.setPassword(passwordEncoder.encode(request.password()));
            player.setRole("PLAYER");
            player.setNickname(request.nickname());
            userRepository.save(player);// esto deberia estar en service
        } else if ("STORE".equalsIgnoreCase(request.role())){
            Store store = new Store();
            store.setEmail(request.email());
            store.setPassword(passwordEncoder.encode(request.password()));
            store.setRole("STORE");
            store.setName(request.storeName());
            store.setAddress(request.address());
            userRepository.save(store);
        }
        return "User registered succesfully";
    }
    @PostMapping("/login")
    public JwtResponse login (@RequestBody LoginRequest request){
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(()-> new RuntimeException(("User not found")));// esto enservice
        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        String token = jwtProvider.generateToken(user.getEmail(), user.getRole());
        return new JwtResponse(token);
    }
}
