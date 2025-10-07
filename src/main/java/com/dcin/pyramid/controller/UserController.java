package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.SignUpRequest;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/update")
    public GeneralResponse updateUser(@AuthenticationPrincipal User user,
                                      @RequestBody SignUpRequest request){
        return userService.updateUser(user, request);
    }

    public GeneralResponse deleteUser(@AuthenticationPrincipal User user){
        return userService.deleteUser(user);
    }
}
