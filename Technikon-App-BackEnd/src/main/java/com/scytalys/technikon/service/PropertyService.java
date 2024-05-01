package com.scytalys.technikon.service;

import com.scytalys.technikon.dto.PropertyDto;
import com.scytalys.technikon.domain.Property;

public interface PropertyService {
    public Property createProperty(Property property);
    public Property searchProperty(PropertyDto propertyDto);
    public Property updateProperty(PropertyDto propertyDto);

}
