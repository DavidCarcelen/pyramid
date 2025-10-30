package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.auth.PlayerSignUpRequest;
import com.dcin.pyramid.model.dto.user.PlayerDTO;
import com.dcin.pyramid.model.dto.user.StoreDTO;
import com.dcin.pyramid.model.dto.user.UserDTO;
import com.dcin.pyramid.model.entity.Player;
import com.dcin.pyramid.model.entity.Store;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pyramid/user")
public class UserController {
    private final UserService userService;

    @PutMapping("/update")
    public ResponseEntity<GeneralResponse> updateUser(@AuthenticationPrincipal User user,
                                      @Valid @RequestBody PlayerSignUpRequest request) {
        return ResponseEntity.ok(userService.updateUser(user, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<GeneralResponse> deleteUser(@AuthenticationPrincipal User user) {

        return ResponseEntity.ok(userService.deleteUser(user));
    }

    @GetMapping ("/my-profile")//okREF
    public ResponseEntity<UserDTO> myProfile (@AuthenticationPrincipal User user){

        return ResponseEntity.ok(userService.getUserDTO(user));
    }

    @PatchMapping("/profile-picture")
    public ResponseEntity<GeneralResponse> uploadProfilePicture(@AuthenticationPrincipal User user,
                                                                @RequestParam("file")MultipartFile file){
        return ResponseEntity.ok(userService.uploadProfilePicture(user, file));
    }

}
