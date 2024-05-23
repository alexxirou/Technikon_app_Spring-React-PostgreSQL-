package com.scytalys.technikon.service;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.dto.PropertyCreateDto;
import com.scytalys.technikon.dto.PropertyUpdateDto;

import java.util.List;

public interface PropertyService {
    //Property Entity & Id Decleration
    Property findPropertyByTin(String tin);
    Property findPropertyById(long Id);
    List<Property> findAllProperties();
    List<Property> findByArea(double latitude, double longitude);
    //CRUD
    PropertyCreateDto createProperty(PropertyCreateDto propertyCreateDto);
    Property updateProperty(long id, PropertyUpdateDto propertyDto);

//    Property deactivateProperty(long id);
  //  Property eraseProperty(long id);
}
