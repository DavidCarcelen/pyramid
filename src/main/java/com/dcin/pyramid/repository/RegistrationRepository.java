package com.dcin.pyramid.repository;

import com.dcin.pyramid.model.entity.Registration;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, UUID> {
    Optional<Registration> findByPlayerIdAndTournamentId(UUID playerId, UUID tournamentId);
    List<Registration> findByTournamentId(UUID tournamentId);
    long countByTournamentIdAndActiveTrue(UUID tournamentId);
    boolean existsByPlayerIdAndTournamentId(UUID playerId, UUID tournamentId);
    @Query("SELECT r.player.nickname FROM Registration r WHERE r.tournament.id = :tournamentId")
    List<String> findPlayerNicknamesByTournamentId(UUID tournamentId);

    @Query("SELECT COUNT(r) FROM Registration r WHERE r.tournament.id = :tournamentId")
    int countPlayersByTournamentId(UUID tournamentId);


}
