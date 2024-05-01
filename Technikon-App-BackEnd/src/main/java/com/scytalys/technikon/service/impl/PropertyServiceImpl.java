package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.dto.PropertyDto;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;

    @Override
    public Property searchProperty(PropertyDto propertyDto) {
        UUID propertyId = propertyDto.propertyId();
        return propertyRepository.findById(propertyId).orElse(null);
    }

    @Override
    public Property createProperty(Property property) {
        return null;
    }

    @Override
    public Property updateProperty(PropertyDto propertyDto) {
        return null;
    }
}
