package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.SignUpRequest;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/update")
    public GeneralResponse updateUser(@AuthenticationPrincipal User user,
                                      @Valid @RequestBody SignUpRequest request) {
        return userService.updateUser(user, request);
    }

    @DeleteMapping("/delete")
    public GeneralResponse deleteUser(@AuthenticationPrincipal User user) {

        return userService.deleteUser(user);
    }
}
