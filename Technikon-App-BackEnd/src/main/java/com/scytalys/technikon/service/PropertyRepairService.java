package com.scytalys.technikon.service;


import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.dto.PropertyRepairDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PropertyRepairService {
    List<PropertyRepairDto> searchPropertyRepairs(long propertyOwnerId);
    PropertyRepairDto createPropertyRepair(PropertyRepairDto propertyRepairDto);
    String updatePropertyRepair(PropertyRepairDto propertyRepairDto, long propertyRepairId) throws IllegalAccessException;
    void deletePropertyRepair(long propertyRepairId);
}