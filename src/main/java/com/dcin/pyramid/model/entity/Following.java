package com.dcin.pyramid.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    private Player player;
    @ManyToOne
    private Store store;

    private LocalDateTime followedAt = LocalDateTime.now();

}
