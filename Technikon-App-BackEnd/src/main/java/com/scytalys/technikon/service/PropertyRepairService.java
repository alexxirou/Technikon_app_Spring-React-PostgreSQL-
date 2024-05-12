package com.scytalys.technikon.service;


import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.dto.repair.*;

import java.math.BigDecimal;
import java.util.List;

public interface PropertyRepairService {

    PropertyRepairDto createPropertyRepair(PropertyRepairDto propertyRepairDto);
    PropertyRepairDto searchPropertyRepair(long propertyOwnerId, long propertyId,long repairId);
    List<PropertyRepairDto> searchPropertyRepairs(long propertyOwnerId, long propertyId);
    List<PropertyRepairDto> searchPropertyRepairByDate(PropertyRepairSearchByDateDto propertyRepairSearchByDateDto);
    List<PropertyRepairDto> searchPropertyRepairByDates(PropertyRepairSearchByDatesDto propertyRepairSearchByDatesDto);
    int updatePropertyRepairByDate(PropertyRepairUpdateByDateDto propertyRepairUpdateByDateDto);
    int updatePropertyRepairByShortDescription(PropertyRepairUpdateByShortDescriptionDto propertyRepairUpdateByShortDescription);
    int updatePropertyRepairByType(PropertyRepairUpdateByTypeDto propertyRepairUpdateByTypeDto);
    int updatePropertyRepairByCost(PropertyRepairUpdateByCostDto propertyRepairUpdateByCostDto);
    int updatePropertyRepairByLongDescription(PropertyRepairUpdateByLongDescriptionDto propertyRepairUpdateByLongDescriptionDto);

    void deletePropertyRepair(long propertyOwnerId, long propertyId, long propertyRepairId);
}