package com.dcin.pyramid.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Team {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;
    @Column(nullable = false, unique = true)
    private String teamName;
    @Column (length = 8, unique = true)
    private String teamEmoji;
    @OneToOne
    @JoinColumn(nullable = false)
    private User founder;
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<User> members = new ArrayList<>();
    private LocalDateTime createdAt = LocalDateTime.now();
}
