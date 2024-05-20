package com.scytalys.technikon.dto;
//Dymmy DTO that returns all the fields of a Property

import com.scytalys.technikon.domain.PropertyOwner;

public record PropertyDto (long id,
                           String address,
                           long propertyId,
                           long propertyOwnerId,
                           double latitude,
                           double longitude,
                           String picture,
                           PropertyOwner propertyOwner){
}

