package com.scytalys.technikon.service;


import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.dto.repair.*;

import java.util.List;

public interface PropertyRepairService {
    PropertyRepairDto createPropertyRepair(PropertyRepairDto propertyRepairDto);
    PropertyRepairDto searchPropertyRepair(long propertyOwnerId, long propertyId,long repairId);
    List<PropertyRepairDto> searchPropertyRepairs(long propertyOwnerId, long propertyId);
    List<PropertyRepairDto> searchPropertyRepairByDate(PropertyRepairSearchByDateDto propertyRepairSearchByDateDto);
    List<PropertyRepairDto> searchPropertyRepairByDates(PropertyRepairSearchByDatesDto propertyRepairSearchByDatesDto);
    PropertyRepairUpdateDto updatePropertyRepair(long id, PropertyRepairUpdateDto propertyRepairUpdateDto);
    void deletePropertyRepair(long id);
}