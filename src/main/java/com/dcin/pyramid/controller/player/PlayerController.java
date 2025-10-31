package com.dcin.pyramid.controller.player;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.auth.PlayerSignUpRequest;
import com.dcin.pyramid.model.dto.user.PlayerDTO;
import com.dcin.pyramid.model.entity.Player;
import com.dcin.pyramid.service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pyramid/player")
@PreAuthorize("hasRole('PLAYER')")
public class PlayerController {
    private final PlayerService playerService;

    @PutMapping("/update")
    public ResponseEntity<GeneralResponse> updatePlayer(@AuthenticationPrincipal Player player,
                                                      @Valid @RequestBody PlayerSignUpRequest request) {
        return ResponseEntity.ok(playerService.updatePlayer(player, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<GeneralResponse> deletePlayer(@AuthenticationPrincipal Player player) {

        return ResponseEntity.ok(playerService.deletePlayer(player));
    }

    @GetMapping("/my-profile")
    public ResponseEntity<PlayerDTO> playerProfile (@AuthenticationPrincipal Player player){

        return ResponseEntity.ok(playerService.getPlayerDTO(player));
    }

    @PatchMapping("/profile-picture")
    public ResponseEntity<GeneralResponse> uploadProfilePicture(@AuthenticationPrincipal Player player,
                                                                @RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(playerService.uploadProfilePicture(player, file));
    }

}
