package com.scytalys.technikon.service;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.dto.PropertyDto;

import java.util.List;

public interface PropertyService {
    //List<Property> findAllPropertyName(Property property);
    Property createProperty(Property property);
    Property searchProperty(PropertyDto propertyDto);
    Property updateProperty(PropertyDto propertyDto);
    Property deactivateProperty(PropertyDto propertyDto);



}
