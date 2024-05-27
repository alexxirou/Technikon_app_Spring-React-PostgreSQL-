package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.dto.property.PropertyCreateDto;
import com.scytalys.technikon.dto.property.PropertyUpdateDto;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.mapper.PropertyMapper;
import com.scytalys.technikon.repository.PropertyRepairRepository;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.service.PropertyRepairService;
import com.scytalys.technikon.service.PropertyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private PropertyRepairService propertyRepairService;

    //Get Property (entity,table_id) - Search
    @Override
    public Property findPropertyByTin(String tin) {
        return propertyRepository.findPropertyByTin(tin);
    }

    @Override
    public Property findPropertyById(long id) {
        return propertyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Property with id " + id + " not found"));
    }

    @Override
    public List<Property> findByArea(double latitude, double longitude) {
        return propertyRepository.findByArea(latitude, longitude);
    }

    @Override
    public List<Property> findAllProperties() {
        return propertyRepository.findAll();
    }

//CRUD

    @Override
    public PropertyCreateDto createProperty(PropertyCreateDto propertyCreateDto) {
        propertyRepository.findById(propertyCreateDto.getPropertyOwnerId());
        Property property = propertyMapper.PropertyCreateDtoToProperty(propertyCreateDto);
        Property newProperty = propertyRepository.save(property);
        return propertyMapper.toPropertyCreateDto(newProperty);
    }

    @Override
    public PropertyUpdateDto updateProperty(long id, PropertyUpdateDto propertyDto) {
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

        return propertyMapper.toPropertyUpdateDto(propertyItem);
    }


    @Transactional
    public void deactivateProperty(Property property) {
      property.setActive(false);
        propertyRepository.save(property);
    }
    @Override
   public boolean checkRelatedEntries(Property property) {
        return propertyRepairService.getPropertyRepairsByOwner(property.getPropertyOwner().getId()).isEmpty();
    }


    @Override
    public void eraseProperty(long id) {
        propertyRepository.deleteById(id);
    }
}


