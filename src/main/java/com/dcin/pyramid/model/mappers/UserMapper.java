package com.dcin.pyramid.model.mappers;

import com.dcin.pyramid.model.dto.UserDTO;
import com.dcin.pyramid.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user){
        String teamName = (user.getTeam() != null) ? user.getTeam().getTeamName() : "";
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getUserName(),
                user.getRole(),
                teamName,
                user.getProfilePictureUrl());

    }
}
