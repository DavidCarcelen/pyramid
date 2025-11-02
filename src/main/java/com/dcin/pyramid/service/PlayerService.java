package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.auth.PlayerSignUpRequest;
import com.dcin.pyramid.model.dto.user.PlayerDTO;
import com.dcin.pyramid.model.dto.user.UpdatePlayerRequest;
import com.dcin.pyramid.model.dto.user.UserDTO;
import com.dcin.pyramid.model.entity.Player;
import com.dcin.pyramid.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface PlayerService {
    GeneralResponse updatePlayer(Player player, UpdatePlayerRequest request);
    GeneralResponse deletePlayer(Player player);
    Player getPlayerById(UUID playerId);
    PlayerDTO getPlayerDTO(Player player);
    GeneralResponse uploadProfilePicture(Player player, MultipartFile file);
}
