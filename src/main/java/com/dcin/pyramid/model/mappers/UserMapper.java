package com.dcin.pyramid.model.mappers;

import com.dcin.pyramid.model.dto.UserDTO;
import com.dcin.pyramid.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user){
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getRole());
    }
}
