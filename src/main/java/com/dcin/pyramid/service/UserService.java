package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.SignUpRequest;
import com.dcin.pyramid.model.dto.UserDTO;
import com.dcin.pyramid.model.entity.Role;
import com.dcin.pyramid.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {
    void checkUserRole(User user, Role role);
    GeneralResponse updateUser(User user, SignUpRequest request);
    GeneralResponse deleteUser(User user);
    User getUserById(UUID userId);
    UserDTO getUserDTO(User user);
    GeneralResponse uploadProfilePicture(User user, MultipartFile file);

}
