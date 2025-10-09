package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.exception.UserAlreadyRegisteredException;
import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.SignUpRequest;
import com.dcin.pyramid.model.entity.Role;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.UserRepository;
import com.dcin.pyramid.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void checkUserRole(User user, Role role) {
        if (user.getRole() != role) {
            throw new AccessDeniedException("Only for " + role.toString());
        }
    }

    @Override
    public void checkId(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UsernameNotFoundException("No user found with that id");
        }

    }

    @Transactional
    @Override
    public GeneralResponse updateUser(User user, SignUpRequest request) {
        User userToUpdate = userRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid user ID."));
        if (!request.nickname().equals(userToUpdate.getNickname()) && userRepository.existsByNickname(request.nickname())) {
            throw new UserAlreadyRegisteredException("Nickname not available.");
        }
        userToUpdate.setNickname(request.nickname());
        userToUpdate.setPassword(passwordEncoder.encode(request.password()));
        userToUpdate.setRole(request.role());
        userToUpdate.setEmail(request.email());
        userRepository.save(userToUpdate);
        return new GeneralResponse("User updated!");
    }

    @Override
    public GeneralResponse deleteUser(User user) {
        User userToDelete = userRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("ID not valid."));
        userRepository.delete(userToDelete);
        return new GeneralResponse("User deleted!");
    }
}
