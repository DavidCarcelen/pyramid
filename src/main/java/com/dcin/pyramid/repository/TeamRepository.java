package com.dcin.pyramid.repository;

import com.dcin.pyramid.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {
    Optional<Team> findByTeamName(String teamName);
    boolean existsByTeamName(String teamName);
    boolean existsByTeamEmoji(String teamEmoji);
}
