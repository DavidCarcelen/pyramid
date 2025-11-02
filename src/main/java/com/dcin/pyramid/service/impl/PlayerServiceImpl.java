package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.exception.EntityNotFoundException;
import com.dcin.pyramid.exception.UserAlreadyRegisteredException;
import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.auth.PlayerSignUpRequest;
import com.dcin.pyramid.model.dto.user.PlayerDTO;
import com.dcin.pyramid.model.dto.user.UpdatePlayerRequest;
import com.dcin.pyramid.model.entity.Player;
import com.dcin.pyramid.repository.PlayerRepository;
import com.dcin.pyramid.service.PlayerService;
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
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public GeneralResponse updatePlayer(Player player, UpdatePlayerRequest request) {
        if (!request.nickname().equals(player.getNickname()) && playerRepository.existsByNickname(request.nickname())) {
            throw new UserAlreadyRegisteredException("Nickname not available.");
        }
        player.setNickname(request.nickname());
        playerRepository.save(player);
        return new GeneralResponse("Player updated!");
    }

    @Override
    public GeneralResponse deletePlayer(Player player) {
        playerRepository.delete(player);
        return new GeneralResponse("Player deleted!");
    }

    @Override
    public Player getPlayerById(UUID playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found."));
    }

    @Override
    public PlayerDTO getPlayerDTO(Player player) {
        String teamName = (player.getTeam() != null) ? player.getTeam().getTeamName() : "";
        return PlayerDTO.builder()
                .id(player.getId().toString())
                .email(player.getEmail())
                .nickname(player.getNickname())
                .profilePictureUrl(player.getProfilePictureUrl())
                .teamName(teamName)
                .build();
    }

    @Override
    public GeneralResponse uploadProfilePicture(Player player, MultipartFile file) {
        try {
            if (player.getProfilePictureUrl() != null) {
                Path oldFilePath = Paths.get(player.getProfilePictureUrl().replace("/uploads/", UPLOAD_DIR));
                Files.delete(oldFilePath);
                player.setProfilePictureUrl(null);
            }
            if (file != null && !file.isEmpty()) {
                String fileName = player.getId() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.write(filePath, file.getBytes());
                String fileURl = "/uploads/" + fileName;
                player.setProfilePictureUrl(fileURl);
            }
            playerRepository.save(player);
            return new GeneralResponse("Profile picture updated successfully!");
        } catch (IOException e) {
            throw new RuntimeException("Error handling profile picture.");
        }
    }
}
