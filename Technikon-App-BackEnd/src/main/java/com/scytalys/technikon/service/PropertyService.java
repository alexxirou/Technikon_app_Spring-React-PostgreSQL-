package com.scytalys.technikon.service;

import com.scytalys.technikon.dto.PropertyDto;
import com.scytalys.technikon.domain.Property;

import java.util.List;

public interface PropertyService {
    //Property Entity & Id Decleration
    Property findProperty(long id);
    List<Property> findAllProperties();
    //CRUD
    Property createProperty(Property property);
    Property searchProperty(PropertyDto propertyDto);
    Property updateProperty(PropertyDto propertyDto);
    Property deactivateProperty(PropertyDto propertyDto);
    Property deleteProperty(PropertyDto propertyDto);
}
