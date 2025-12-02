package com.dcin.pyramid.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Column(name = "address_country")
    private String country;

    @Column(name = "address_city")
    private String city;

    @Column(name = "address_google_maps_link")
    private String googleMapsLink;
}
