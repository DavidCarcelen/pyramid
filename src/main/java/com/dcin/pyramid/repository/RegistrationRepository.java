package com.dcin.pyramid.repository;

import com.dcin.pyramid.model.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, UUID> {
    Optional<Registration> findByPlayerIdAndTournamentId(UUID playerId, UUID tournamentId);
    List<Registration> findByTournamentId(UUID tournamentId);
    long countByTournamentIdAndActiveTrue(UUID tournamentId);
}
