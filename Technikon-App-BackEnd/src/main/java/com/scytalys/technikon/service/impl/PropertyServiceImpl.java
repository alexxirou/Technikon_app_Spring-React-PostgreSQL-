package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.dto.PropertyDto;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;

    @Override
    public Property searchProperty(long propertyId) {
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
}
