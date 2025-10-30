package com.dcin.pyramid.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;
@Entity
public class PlayerAccount {
    @Id
    @GeneratedValue
    private UUID id;
    private BigDecimal balance = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Player player;

    @ManyToOne//check relation
    @JoinColumn(nullable = false)
    private Store store;

}
