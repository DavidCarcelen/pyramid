package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.exception.EntityNotFoundException;
import com.dcin.pyramid.exception.RoleException;
import com.dcin.pyramid.exception.UserAlreadyRegisteredException;
import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.auth.PlayerSignUpRequest;
import com.dcin.pyramid.model.dto.user.UserDTO;
import com.dcin.pyramid.model.dto.auth.Role;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.model.mappers.UserMapper;
import com.dcin.pyramid.repository.UserRepository;
import com.dcin.pyramid.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Transactional
    @Override
    public GeneralResponse updateUser(User user, PlayerSignUpRequest request) {
        User userToUpdate = getUserById(user.getId());
        if (!request.nickname().equals(userToUpdate.getNickname()) && userRepository.existsByNickname(request.nickname())) {
            throw new UserAlreadyRegisteredException("Nickname not available.");
        }
        userToUpdate.setNickname(request.nickname());
        userToUpdate.setPassword(passwordEncoder.encode(request.password()));
        userToUpdate.setEmail(request.email());
        userRepository.save(userToUpdate);
        return new GeneralResponse("User updated!");
    }

    @Override
    public GeneralResponse deleteUser(User user) {
        userRepository.delete(user);
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
        try {
            // Case 1: No file in the request → remove current picture
            if (file == null || file.isEmpty()) {
                if (user.getProfilePictureUrl() != null) {
                    // Delete the old file from disk
                    Path oldFilePath = Paths.get(user.getProfilePictureUrl().replace("/uploads/", UPLOAD_DIR));
                    Files.deleteIfExists(oldFilePath);
                }

                user.setProfilePictureUrl(null);
                userRepository.save(user);
                return new GeneralResponse("Profile picture removed successfully!");
            }

            // Case 2: File provided → replace or add new picture
            // Remove old one first (if exists)
            if (user.getProfilePictureUrl() != null) {
                Path oldFilePath = Paths.get(user.getProfilePictureUrl().replace("/uploads/", UPLOAD_DIR));
                Files.deleteIfExists(oldFilePath);
            }

            // Build new file name and save it
            String fileName = user.getId() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.write(filePath, file.getBytes());

            // Update user record
            String fileUrl = "/uploads/" + fileName; // used when serving static files
            user.setProfilePictureUrl(fileUrl);
            userRepository.save(user);

            return new GeneralResponse("Profile picture uploaded successfully!");
        } catch (IOException e) {
            throw new RuntimeException("Error handling profile picture: " + e.getMessage());
        }
    }


}
