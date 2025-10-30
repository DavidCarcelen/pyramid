package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.auth.PlayerSignUpRequest;
import com.dcin.pyramid.model.dto.user.UserDTO;
import com.dcin.pyramid.model.dto.auth.Role;
import com.dcin.pyramid.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {
    GeneralResponse updateUser(User user, PlayerSignUpRequest request);
    GeneralResponse deleteUser(User user);
    User getUserById(UUID userId);
    UserDTO getUserDTO(User user);
    GeneralResponse uploadProfilePicture(User user, MultipartFile file);

}
