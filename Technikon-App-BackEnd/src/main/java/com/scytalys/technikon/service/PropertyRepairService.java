package com.scytalys.technikon.service;


import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.dto.PropertyRepairDto;

import java.time.LocalDate;
import java.util.List;

public interface PropertyRepairService {
    PropertyRepair createPropertyRepair(PropertyRepair propertyRepair);
    List<PropertyRepair> getPropertyRepairByDate(LocalDate date);
    List<PropertyRepair> getPropertyRepairByDates(LocalDate firstDate, LocalDate lastDate);
    List<PropertyRepair> getPropertyRepairs(long propertyOwnerId);
    boolean updatePropertyRepairByDate(long propertyRepairId, LocalDate date);
    boolean updatePropertyRepairByShortDescription(long propertyRepairId, String shortDescription);
    boolean updatePropertyRepairByRepairType(long propertyRepairId, RepairType repairType);
    boolean updatePropertyRepairByCost(long propertyRepairId, double cost);
    boolean updatePropertyRepairByLongDescription(long propertyRepairId, String longDescription);
    void deletePropertyRepair(long propertyRepairId);
}