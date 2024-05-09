package com.scytalys.technikon.service;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.dto.PropertyDto;

public interface PropertyService {
     Property createProperty(Property property);
     Property searchProperty(PropertyDto propertyDto);
     Property updateProperty(PropertyDto propertyDto);

}
