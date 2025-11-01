package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.registration.RegistrationsResponse;
import com.dcin.pyramid.model.entity.Player;
import com.dcin.pyramid.model.entity.Store;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;

import java.util.UUID;

public interface RegistrationService {
    RegistrationsResponse handleRegistration(Player player, UUID tournamentId);
    RegistrationsResponse newRegistration(Player player, Tournament tournament, boolean reserveList);
    GeneralResponse deleteRegistration(User user, UUID registrationId);
    RegistrationsResponse getAllRegistrations(UUID tournamentId);
    GeneralResponse markAsPaid (Store store, UUID registrationId);
    boolean promotePlayerRegistration(UUID tournamentId);

}
