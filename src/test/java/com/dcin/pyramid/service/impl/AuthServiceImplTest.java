package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.exception.UserAlreadyRegisteredException;
import com.dcin.pyramid.model.dto.auth.JwtResponse;
import com.dcin.pyramid.model.dto.auth.LoginRequest;
import com.dcin.pyramid.model.dto.auth.PlayerSignUpRequest;
import com.dcin.pyramid.model.dto.auth.StoreSignUpRequest;
import com.dcin.pyramid.model.entity.Player;
import com.dcin.pyramid.model.entity.Store;
import com.dcin.pyramid.repository.PlayerRepository;
import com.dcin.pyramid.repository.StoreRepository;
import com.dcin.pyramid.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    private LoginRequest loginRequest;
    private PlayerSignUpRequest playerRequest;
    private StoreSignUpRequest storeRequest;
    private Authentication authentication;
    private Player player;
    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest("user@email.com", "password");
        playerRequest = new PlayerSignUpRequest("user@email.com", "password", "nickname");
        storeRequest = new StoreSignUpRequest("user@email.com", "password", "nickname", "address");
        player = Player.builder()
                .email(loginRequest.email())
                .password(loginRequest.password())
                .nickname("nickname")
                .build();

        authentication = new UsernamePasswordAuthenticationToken(player, null);
    }

    @Test
    void login_ShouldReturnJwtResponse_WhenCredentialsValid() {
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtProvider.generateToken(player)).thenReturn("mockToken");

        JwtResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("mockToken", response.token());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtProvider).generateToken(player);
    }

    @Test
    void playerSignup_ShouldSavePlayerAndReturnToken_WhenNewEmailAndNickname() {
        when(playerRepository.existsByEmail("user@email.com")).thenReturn(false);
        when(playerRepository.existsByNickname("nickname")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPass");
        when(jwtProvider.generateToken(any(Player.class))).thenReturn("mockToken");

        JwtResponse response = authService.playerSignup(playerRequest);

        assertNotNull(response);
        assertEquals("mockToken", response.token());
        verify(playerRepository).save(any(Player.class));
        verify(jwtProvider).generateToken(any(Player.class));
    }

    @Test
    void playerSignup_ShouldThrowException_WhenEmailAlreadyExists() {
        when(playerRepository.existsByEmail("user@email.com")).thenReturn(true);

        assertThrows(UserAlreadyRegisteredException.class, () -> authService.playerSignup(playerRequest));

        verify(playerRepository, never()).save(any());
        verify(jwtProvider, never()).generateToken(any());
    }

    @Test
    void playerSignup_ShouldThrowException_WhenNicknameAlreadyExists() {
        when(playerRepository.existsByEmail("user@email.com")).thenReturn(false);
        when(playerRepository.existsByNickname("nickname")).thenReturn(true);

        assertThrows(UserAlreadyRegisteredException.class, () -> authService.playerSignup(playerRequest));

        verify(playerRepository, never()).save(any());
        verify(jwtProvider, never()).generateToken(any());
    }
    @Test
    void storeSignup_ShouldSaveStoreAndReturnToken_WhenNewEmailAndNickname() {
        when(storeRepository.existsByEmail("user@email.com")).thenReturn(false);
        when(storeRepository.existsByNickname("nickname")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPass");
        when(jwtProvider.generateToken(any(Store.class))).thenReturn("mockToken");

        JwtResponse response = authService.storeSignup(storeRequest);

        assertNotNull(response);
        assertEquals("mockToken", response.token());
        verify(storeRepository).save(any(Store.class));
        verify(jwtProvider).generateToken(any(Store.class));
    }

    @Test
    void storeSignup_ShouldThrowException_WhenEmailAlreadyExists() {
        when(storeRepository.existsByEmail("user@email.com")).thenReturn(true);

        assertThrows(UserAlreadyRegisteredException.class, () -> authService.storeSignup(storeRequest));

        verify(storeRepository, never()).save(any());
        verify(jwtProvider, never()).generateToken(any());
    }

    @Test
    void storeSignup_ShouldThrowException_WhenNicknameAlreadyExists() {
        when(storeRepository.existsByEmail("user@email.com")).thenReturn(false);
        when(storeRepository.existsByNickname("nickname")).thenReturn(true);

        assertThrows(UserAlreadyRegisteredException.class, () -> authService.storeSignup(storeRequest));

        verify(storeRepository, never()).save(any());
        verify(jwtProvider, never()).generateToken(any());
    }
}

