package com.dcin.pyramid.repository;

import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface TournamentRepository extends JpaRepository<Tournament, UUID> {
    List<Tournament> findByOrganizerId(UUID storeId);
    List<Tournament> findByOrganizerAndDateAfter(User organizer, LocalDate date);

}
