package com.dcin.pyramid.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tournament {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String tournamentName;
    @Column(nullable = false)
    private LocalDateTime startDateTime;
    private int maxPlayers;
    private String format;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prizeMoney;
    private boolean open;
    private boolean full;
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    private List<Registration> registrations;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User organizer;
}
