package com.dcin.pyramid.model.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class Address {
    private String country;
    private String city;
    private String googleMapsLink;
}
