package com.dcin.pyramid.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // cada hijo en su tabla
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class User {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // hash en el futuro

    @Column(nullable = false)
    private String role; // "PLAYER" o "STORE"

    private LocalDateTime createdAt = LocalDateTime.now();

}

