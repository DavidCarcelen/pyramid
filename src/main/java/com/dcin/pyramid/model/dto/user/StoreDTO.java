package com.dcin.pyramid.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StoreDTO extends UserDTO{
    private String country;
    private String city;
    private String googleMapsLink;
    private int storeCapacity;
    private String cardMarketLink;
}
