package com.scytalys.technikon.service;

import com.scytalys.technikon.dto.PropertyOwnerDto;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.dto.PropertyOwnerUpdateDto;
import com.scytalys.technikon.dto.PropertyRepairDto;

import java.util.UUID;

public interface PropertyOwnerService {

    public PropertyOwnerDto createPropertyOwner(PropertyOwner propertyOwner);
    public PropertyOwner searchPropertyOwner(PropertyRepairDto propertyOwnerDto);
    public boolean updatePropertyOwner(PropertyOwnerUpdateDto propertyOwnerUpdateDto);
    public boolean deletePropertyOwner(UUID propertyOwnerId);

}
