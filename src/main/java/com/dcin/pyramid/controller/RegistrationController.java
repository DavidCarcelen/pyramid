package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.registration.RegistrationsResponse;
import com.dcin.pyramid.model.entity.Player;
import com.dcin.pyramid.model.entity.Store;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pyramid/registrations")
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/{tournamentId}")
    public ResponseEntity<RegistrationsResponse> newRegistration(@AuthenticationPrincipal Player player,// store?
                                                                 @PathVariable UUID tournamentId) {
        return ResponseEntity.ok(registrationService.handleRegistration(player, tournamentId));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<GeneralResponse> deleteRegistration(@AuthenticationPrincipal User user,//create second method for store?
                                                              @PathVariable UUID registrationId) {
        return ResponseEntity.ok(registrationService.deleteRegistration(user, registrationId));
    }

    @GetMapping("/{tournamentId}")
    public ResponseEntity<RegistrationsResponse> getRegisteredPlayersForOneTournament(@PathVariable UUID tournamentId) {
        return ResponseEntity.ok(registrationService.getAllRegistrations(tournamentId));
    }

    @PreAuthorize("hasRole('STORE')")
    @PatchMapping("/{registrationId}/mark-paid")
    public ResponseEntity<GeneralResponse> markRegistrationAsPaid(@AuthenticationPrincipal Store store,
                                                                  @PathVariable UUID registrationId) {
        return ResponseEntity.ok(registrationService.markAsPaid(store, registrationId));
    }

}
