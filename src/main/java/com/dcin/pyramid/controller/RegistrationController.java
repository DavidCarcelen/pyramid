package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/registrations")
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/{tournamentId}")
    public ResponseEntity<GeneralResponse> newRegistration(@AuthenticationPrincipal User player,
                                                           @PathVariable UUID tournamentId){
        return ResponseEntity.ok(registrationService.newRegistration(player,tournamentId));
    }
    @DeleteMapping("/{tournamentId}")
    public ResponseEntity<GeneralResponse> deleteRegistration(@AuthenticationPrincipal User player,
                                                              @PathVariable UUID tournamentId){
        return ResponseEntity.ok(registrationService.deleteRegistration(player,tournamentId));
    }
}
