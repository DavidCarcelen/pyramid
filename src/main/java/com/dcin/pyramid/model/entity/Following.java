package com.dcin.pyramid.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Following {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Player follower;

    @ManyToOne
    @JoinColumn(name = "followed_store_id")
    private Store followedStore;

    private LocalDateTime followedAt = LocalDateTime.now();

}
