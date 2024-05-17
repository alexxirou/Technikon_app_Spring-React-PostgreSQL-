package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.dto.PropertyDto;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.service.PropertyService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;

    @Override
    @Transactional
    public List<Property> findAllPropertyName(Property property) {
        return propertyRepository.findAll(); }

    @Override
    public Property searchProperty(PropertyDto propertyDto) {
        long propertyId = propertyDto.propertyId();
        return propertyRepository.findById(propertyId).orElse(null);
    }

    @Override
    public Property createProperty(Property property) {
        propertyRepository.save(property);
        return property;
    }

    @Override
    public Property updateProperty(PropertyDto propertyDto) {
        return null;
    }

    @Override
    public Property deactivateProperty(PropertyDto propertyDto) { return null;}


}
