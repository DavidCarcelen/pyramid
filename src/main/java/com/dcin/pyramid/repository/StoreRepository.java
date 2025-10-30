package com.dcin.pyramid.repository;

import com.dcin.pyramid.model.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {
    Optional<Store> findByEmail(String email);
    Optional<Store> findByNickname(String nickname);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

}
