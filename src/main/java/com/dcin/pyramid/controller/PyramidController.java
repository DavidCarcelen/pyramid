package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.NewTournamentRequest;
import com.dcin.pyramid.model.dto.TournamentCreationResponse;
import com.dcin.pyramid.model.entity.Role;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.service.PyramidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class PyramidController {
    private final PyramidService pyramidService;

    @PostMapping("/tournaments")
    public ResponseEntity<TournamentCreationResponse> createTournament(@RequestBody NewTournamentRequest request,
                                                                       @AuthenticationPrincipal User user){
        if (user.getRole() != Role.STORE){
            throw new AccessDeniedException("Only Stores can create tournaments!");
        }

        return ResponseEntity.ok().body(pyramidService.createTournament(request, user));
    }
}
