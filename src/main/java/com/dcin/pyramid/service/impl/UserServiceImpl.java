package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.exception.EntityNotFoundException;
import com.dcin.pyramid.exception.RoleException;
import com.dcin.pyramid.exception.UserAlreadyRegisteredException;
import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.SignUpRequest;
import com.dcin.pyramid.model.dto.UserDTO;
import com.dcin.pyramid.model.entity.Role;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.model.mappers.UserMapper;
import com.dcin.pyramid.repository.UserRepository;
import com.dcin.pyramid.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public void checkUserRole(User user, Role role) {
        if (user.getRole() != role) {
            throw new RoleException(role.toString());
        }
    }

    @Transactional
    @Override
    public GeneralResponse updateUser(User user, SignUpRequest request) {
        User userToUpdate = getUserById(user.getId());
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
        User userToDelete = getUserById(user.getId());
        userRepository.delete(userToDelete);
        return new GeneralResponse("User deleted!");
    }

    @Override
    public User getUserById(UUID userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
    }

    @Override
    public UserDTO getUserDTO(User user) {
        return userMapper.toDTO(user);
    }

    @Override
    public GeneralResponse uploadProfilePicture(User user, MultipartFile file) {
        // Build file path (use UUID to avoid duplicates)
        String fileName = user.getId() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);

        try {
            // Save file locally
            Files.write(filePath, file.getBytes());

            // Build file URL (this assumes you’ll serve static resources from /uploads)
            String fileUrl = "/uploads/" + fileName;

            // Update user
            user.setProfilePictureUrl(fileUrl);
            userRepository.save(user);

            return new GeneralResponse("Profile picture uploaded successfully!");
        } catch (IOException e) {
            throw new RuntimeException("Error saving profile picture: " + e.getMessage());
        }


    }

}
