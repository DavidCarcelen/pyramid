package com.dcin.pyramid.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "stores")
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Store extends User{
    @Embedded
    private Address address;
    private int storeCapacity;
    private String cardMarketLink;

    @OneToMany(mappedBy = "organizer")
    private List<Tournament> tournaments;

    @OneToMany(mappedBy = "store")
    private List<PlayerAccount> accounts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_STORE"));
    }
}
