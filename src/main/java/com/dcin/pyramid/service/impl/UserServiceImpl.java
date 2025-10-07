package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.SignUpRequest;
import com.dcin.pyramid.model.entity.Role;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.UserRepository;
import com.dcin.pyramid.service.UserService;
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
    public User checkUserName(String name){
        return userRepository.findByNickname(name).orElseThrow(()-> new UsernameNotFoundException("No user found with the given name."));
    }

    @Override
    public void checkId(UUID id) {
        if(!userRepository.existsById(id)){
            throw new UsernameNotFoundException("No user found with that id");
        }

    }

    @Override
    public GeneralResponse updateUser(User user, SignUpRequest request) {
        if (request.email().isEmpty() || request.password().isEmpty() || request.nickname().isEmpty() || (request.role() == null)){
            throw new IllegalArgumentException("Fields can't be empty.");
        }
        User userToUpdate = userRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("ID not valid."));
        userToUpdate.setNickname(request.nickname());
        userToUpdate.setPassword(passwordEncoder.encode(request.password()));
        userToUpdate.setRole(request.role());
        userToUpdate.setEmail(request.email());
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
