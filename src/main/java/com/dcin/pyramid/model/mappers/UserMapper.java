package com.dcin.pyramid.model.mappers;

import com.dcin.pyramid.model.dto.user.PlayerDTO;
import com.dcin.pyramid.model.dto.user.StoreDTO;
import com.dcin.pyramid.model.dto.user.UserDTO;
import com.dcin.pyramid.model.entity.Player;
import com.dcin.pyramid.model.entity.Store;
import com.dcin.pyramid.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user){
        UserDTO userDTO = null;
        if (user instanceof Player player){
            String teamName = (player.getTeam() != null) ? player.getTeam().getTeamName() : "";
            userDTO = PlayerDTO.builder()
                    .id(player.getId().toString())
                    .email(player.getEmail())
                    .nickname(player.getNickname())
                    .profilePictureUrl(player.getProfilePictureUrl())
                    .teamName(teamName)
                    .build();
        } else if(user instanceof Store store){
            userDTO = StoreDTO.builder()
                    .id(store.getId().toString())
                    .email(store.getEmail())
                    .nickname(store.getNickname())
                    .profilePictureUrl(store.getProfilePictureUrl())
                    .address(store.getAddress())
                    .build();
        }
        return userDTO;
    }
}
