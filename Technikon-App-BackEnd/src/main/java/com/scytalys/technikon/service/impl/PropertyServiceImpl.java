package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.dto.PropertyCreateDto;
import com.scytalys.technikon.dto.PropertyUpdateDto;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.mapper.PropertyMapper;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.service.PropertyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;

//Get Property (entity,table_id) - Search
    @Override
    public Property findPropertyByTin(String tin) {
        return propertyRepository.findPropertyByTin(tin);
    }

    @Override
    public Property findPropertyById(long id) {
        return propertyRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Property with id "+ id+ " not found"));
    }

    @Override
    public List<Property> findByArea(double latitude, double longitude){
        return propertyRepository.findByArea(latitude, longitude);
    }

    @Override
    public List<Property> findAllProperties() {
       return propertyRepository.findAll(); }

//CRUD

    @Override
    public PropertyCreateDto createProperty(PropertyCreateDto propertyCreateDto) {
        Property property = propertyMapper.PropertyCreateDtoToProperty(propertyCreateDto);
        propertyRepository.save(property);
        return propertyCreateDto;
    }

    @Override
    public Property updateProperty(long id, PropertyUpdateDto propertyDto) {
        Property propertyItem = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Property with id " + id + " not found"));

        if (propertyDto != null) {
            propertyItem.setConstructionYear(propertyDto.getConstructionYear());
            propertyItem.setAddress(propertyDto.getAddress());
            propertyItem.setPropertyType(propertyDto.getPropertyType());
            propertyItem.setLatitude(propertyDto.getLatitude());
            propertyItem.setLongitude(propertyDto.getLongitude());
            propertyItem.setPicture(propertyDto.getPicture());
            propertyRepository.save(propertyItem);
        }

        return propertyItem;
    }

//    @Override
//    public Property deactivateProperty(PropertyUpdateDto propertyDto) { return null;}
//
//      @Override
//      public Property eraseProperty(long id){
//      Property propertyItem = propertyRepository.eraseProperty.orElseThrow(() -> new EntityNotFoundException("Property  with id "+ id+ " not found"));
//      if (propertyItem == null) {
//           propertyRepository.delete(property);
//           return true;
//       }
//      return false;
//      }

}


