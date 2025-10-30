package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.registration.RegistrationsResponse;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;

import java.util.UUID;

public interface RegistrationService {
    RegistrationsResponse handleRegistration(User player, UUID tournamentId);
    RegistrationsResponse newRegistration(User player, Tournament tournament, boolean reserveList);
    GeneralResponse deleteRegistration(User player, UUID registrationtId);
    RegistrationsResponse getAllRegistrations(UUID tournamentId);
    GeneralResponse markAsPaid (User store, UUID registrationId);
    boolean promotePlayerRegistration(UUID tournamentId);

}
