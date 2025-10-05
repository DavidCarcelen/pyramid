package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.NewTournamentRequest;
import com.dcin.pyramid.model.dto.TournamentManagementResponse;
import com.dcin.pyramid.model.entity.Role;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.service.PyramidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PyramidController {
    private final PyramidService pyramidService;

    @PostMapping("/tournaments")
    public ResponseEntity<TournamentManagementResponse> createTournament(@RequestBody NewTournamentRequest request,
                                                                         @AuthenticationPrincipal User user){
        if (user.getRole() != Role.STORE){
            throw new AccessDeniedException("Only Stores can create tournaments!");
        }

        return ResponseEntity.ok(pyramidService.createTournament(request, user));
    }

    @GetMapping("tournaments")
    public ResponseEntity<TournamentManagementResponse> getMyTournaments(@AuthenticationPrincipal User user){
        if (user.getRole() != Role.STORE){
            throw new AccessDeniedException("Only Stores have tournaments!");
        }
        return ResponseEntity.ok(pyramidService.getTournamentsByOrganizer(user, LocalDate.of(2025,1,1), "Your Tournaments!:"));
    }


    //upcommingtournaments(store or not)
    //upcommingmy favouritestores
}
