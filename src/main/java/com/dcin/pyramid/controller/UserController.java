package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.SignUpRequest;
import com.dcin.pyramid.model.dto.TeamRequest;
import com.dcin.pyramid.model.dto.UserDTO;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.service.TeamService;
import com.dcin.pyramid.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final TeamService teamService;

    @PutMapping("/update")
    public ResponseEntity<GeneralResponse> updateUser(@AuthenticationPrincipal User user,
                                      @Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(userService.updateUser(user, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<GeneralResponse> deleteUser(@AuthenticationPrincipal User user) {

        return ResponseEntity.ok(userService.deleteUser(user));
    }

    @GetMapping ("/myProfile")
    public ResponseEntity<UserDTO> myProfile (@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.getUserDTO(user));
    }

    @PatchMapping("/profilePicture")
    public ResponseEntity<GeneralResponse> uploadProfilePicture(@AuthenticationPrincipal User user,
                                                                @RequestParam("file")MultipartFile file){
        return ResponseEntity.ok(userService.uploadProfilePicture(user, file));
    }

    @PostMapping("/team")
    public ResponseEntity<GeneralResponse>createTeam(@AuthenticationPrincipal User user,@RequestBody TeamRequest teamRequest){
        return ResponseEntity.ok(teamService.createTeam(user, teamRequest));
    }
}
