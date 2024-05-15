package com.scytalys.technikon.service;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.dto.repair.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface PropertyRepairService {
    PropertyRepairDto createPropertyRepair(PropertyRepairDto propertyRepairDto);
    PropertyRepairDto getPropertyRepair(long repairId);
    List<PropertyRepairDto> getAllPropertyRepairs();
    List<PropertyRepairDto> getPropertyRepairsByOwner(long propertyOwnerId);
    List<PropertyRepairDto> searchPropertyRepairByDate(PropertyRepairSearchByDateDto propertyRepairSearchByDateDto);
    List<PropertyRepairDto> searchPropertyRepairByDates(PropertyRepairSearchByDatesDto propertyRepairSearchByDatesDto);
    PropertyRepairUpdateDto updatePropertyRepair(long id, PropertyRepairUpdateDto propertyRepairUpdateDto);
    void deletePropertyRepair(long id);
}