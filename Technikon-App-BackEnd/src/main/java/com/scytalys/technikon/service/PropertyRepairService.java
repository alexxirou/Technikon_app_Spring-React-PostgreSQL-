package com.scytalys.technikon.service;


import com.scytalys.technikon.dto.PropertyRepairDto;

import java.time.LocalDate;
import java.util.List;

public interface PropertyRepairService {

    PropertyRepairDto createPropertyRepair(PropertyRepairDto propertyRepairDto);
    List<PropertyRepairDto> searchPropertyRepairs(long propertyOwnerId);
    List<PropertyRepairDto> searchPropertyRepairByDate(long propertyOwnerId, LocalDate date);
    List<PropertyRepairDto> searchPropertyRepairByDates(long propertyOwnerId, LocalDate firstDate, LocalDate lastDate);
    void updatePropertyRepair(long propertyOwnerId,long propertyRepairId,PropertyRepairDto propertyRepairDto);
    void deletePropertyRepair(long propertyOwnerId, long propertyRepairId);
}