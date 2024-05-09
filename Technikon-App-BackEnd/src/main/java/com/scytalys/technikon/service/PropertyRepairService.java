package com.scytalys.technikon.service;


import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.dto.PropertyRepairCreationDto;
import com.scytalys.technikon.dto.PropertyRepairDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PropertyRepairService {

    PropertyRepairCreationDto createPropertyRepair(PropertyRepairCreationDto propertyRepairCreationDto);
    List<PropertyRepairDto> searchPropertyRepairs(long propertyOwnerId);
    List<PropertyRepairDto> searchPropertyRepairByDate(long propertyOwnerId, LocalDate date);
    List<PropertyRepairDto> searchPropertyRepairByDates(long propertyOwnerId, LocalDate firstDate, LocalDate lastDate);
    int updatePropertyRepairByDate(long propertyRepairId, LocalDate date);
    int updatePropertyRepairByShortDescription(long propertyRepairId, String shortDescription);
    int updatePropertyRepairByRepairType(long propertyRepairId, RepairType repairType);
    int updatePropertyRepairByCost(long propertyRepairId, BigDecimal cost);
    int updatePropertyRepairByLongDescription(long propertyRepairId, String longDescription);
    void updatePropertyRepair(long propertyOwnerId,long propertyRepairId,PropertyRepairDto propertyRepairDto);
//    void softDeletePropertyRepair(long propertyOwnerId, long propertyRepairId);
    void deletePropertyRepair(long propertyOwnerId, long propertyRepairId);
}