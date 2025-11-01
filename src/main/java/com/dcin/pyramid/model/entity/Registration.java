package com.dcin.pyramid.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"player_id", "tournament_id"})
        }
)
public class Registration {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "player_id",nullable = false)
    private Player player;

    @ManyToOne
    @JoinColumn(name = "tournament_id",nullable = false)
    private Tournament tournament;

    private boolean paid;
    private LocalDateTime registeredAt;
    private boolean reserveList;

    @PrePersist
    public void prePersist() {
        if (registeredAt == null) {
            registeredAt = LocalDateTime.now();
        }
    }
}
