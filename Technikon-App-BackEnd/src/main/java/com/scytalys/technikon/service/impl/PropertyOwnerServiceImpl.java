package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.dto.PropertyOwnerDto;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.dto.PropertyOwnerUpdateDto;
import com.scytalys.technikon.dto.PropertyRepairDto;
import com.scytalys.technikon.service.PropertyOwnerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PropertyOwnerServiceImpl implements PropertyOwnerService {

    @Override
    public PropertyOwnerDto createPropertyOwner(PropertyOwner propertyOwner) {
        return null;
    }
    @Override
    public PropertyOwner searchPropertyOwner(PropertyRepairDto propertyOwnerDto) {
        return null;
    }

    @Override
    public boolean updatePropertyOwner(PropertyOwnerUpdateDto propertyOwnerUpdateDto) {
        return false;
    }

    @Override
    public boolean deletePropertyOwner(UUID propertyOwnerId) {
        return false;
    }
}
