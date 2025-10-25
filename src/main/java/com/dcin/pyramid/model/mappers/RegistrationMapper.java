package com.dcin.pyramid.model.mappers;

import com.dcin.pyramid.model.dto.RegistrationInfoDTO;
import com.dcin.pyramid.model.entity.Registration;
import org.springframework.stereotype.Component;

@Component
public class RegistrationMapper {
    public RegistrationInfoDTO toDto(Registration registration){
        return new RegistrationInfoDTO(
                registration.getId(),
                registration.getPlayer().getNickname(),
                registration.getPlayer().getTeam().getTeamEmoji(),
                registration.isReserveList(),
                registration.getRegisteredAt()
        );
    }
}
