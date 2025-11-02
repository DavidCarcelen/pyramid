package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.exception.EntityNotFoundException;
import com.dcin.pyramid.exception.UserAlreadyRegisteredException;
import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.auth.StoreSignUpRequest;
import com.dcin.pyramid.model.dto.user.StoreDTO;
import com.dcin.pyramid.model.dto.user.UpdateStoreRequest;
import com.dcin.pyramid.model.entity.Store;
import com.dcin.pyramid.repository.StoreRepository;
import com.dcin.pyramid.service.StoreService;
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
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public GeneralResponse updateStore(Store store, UpdateStoreRequest request) {
        if (!request.nickname().equals(store.getNickname()) && storeRepository.existsByNickname(request.nickname())) {
            throw new UserAlreadyRegisteredException("Store name not available.");
        }
        store.setNickname(request.nickname());
        store.setAddress(request.address());
        storeRepository.save(store);
        return new GeneralResponse("Store updated!");
    }

    @Override
    public GeneralResponse deleteStore(Store store) {
        storeRepository.delete(store);
        return new GeneralResponse("Store deleted!");
    }

    @Override
    public Store getStoreById(UUID storeId) {// accessible for player????
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("Store not found."));
    }

    @Override
    public StoreDTO getStoreDTO(Store store) {
        return StoreDTO.builder()
                .id(store.getId().toString())
                .email(store.getEmail())
                .nickname(store.getNickname())
                .profilePictureUrl(store.getProfilePictureUrl())
                .address(store.getAddress())
                .build();
    }

    @Override
    public GeneralResponse uploadProfilePicture(Store store, MultipartFile file) {
        try {
            if (store.getProfilePictureUrl() != null) {
                Path oldFilePath = Paths.get(store.getProfilePictureUrl().replace("/uploads/", UPLOAD_DIR));
                Files.delete(oldFilePath);
                store.setProfilePictureUrl(null);
            }
            if (file != null && !file.isEmpty()) {
                String fileName = store.getId() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.write(filePath, file.getBytes());
                String fileURl = "/uploads/" + fileName;
                store.setProfilePictureUrl(fileURl);
            }
            storeRepository.save(store);
            return new GeneralResponse("Profile picture updated successfully!");
        } catch (IOException e) {
            throw new RuntimeException("Error handling profile picture."); //why
        }
    }
}
