package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.model.entity.Role;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.UserRepository;
import com.dcin.pyramid.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

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
}
