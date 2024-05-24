package com.scytalys.technikon.service;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.dto.property.PropertyCreateDto;
import com.scytalys.technikon.dto.property.PropertyUpdateDto;

import java.util.List;

public interface PropertyService {
    //Property Entity & Id Decleration
    Property findPropertyByTin(String tin);
    Property findPropertyById(long Id);
    List<Property> findAllProperties();
    List<Property> findByArea(double latitude, double longitude);
    //CRUD
    PropertyCreateDto createProperty(PropertyCreateDto propertyCreateDto);
    PropertyUpdateDto updateProperty(long id, PropertyUpdateDto propertyDto);

//    PropertyDeactivateDto deactivateProperty(long id);
  //  PropertyErase eraseProperty(long id);
}
