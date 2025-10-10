package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.RegistrationsResponse;
import com.dcin.pyramid.model.entity.Registration;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;

import java.util.UUID;

public interface RegistrationService {
    RegistrationsResponse checkRegistrationAvailability(User player, UUID tournamentId);
    RegistrationsResponse newRegistration(User player, UUID tournamentId);
    RegistrationsResponse newReserveListRegistration(User player, UUID tournamentId);
    GeneralResponse deleteRegistration(User player, UUID tournamentId);
    RegistrationsResponse getRegistrations(UUID tournamentId);
    RegistrationsResponse getReserveRegistrations(UUID tournamentId);

}
