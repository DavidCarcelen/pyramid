package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.SignUpRequest;
import com.dcin.pyramid.model.entity.Role;
import com.dcin.pyramid.model.entity.User;

import java.util.UUID;

public interface UserService {
    void checkUserRole(User user, Role role);
    User checkUserName(String name);
    void checkId(UUID id);
    GeneralResponse updateUser(User user, SignUpRequest request);
    GeneralResponse deleteUser(User user);
}
