package com.scytalys.technikon.service;


import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.dto.PropertyRepairDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface PropertyRepairService {

    PropertyRepairDto createPropertyRepair(PropertyRepairDto propertyRepairDto);
    List<PropertyRepairDto> searchPropertyRepairs(long propertyOwnerId);
    List<PropertyRepairDto> searchPropertyRepairByDate(long propertyOwnerId, LocalDate date);
    List<PropertyRepairDto> searchPropertyRepairByDates(long propertyOwnerId, LocalDate firstDate, LocalDate lastDate )
    String updatePropertyRepair(PropertyRepairDto propertyRepairDto, long propertyRepairId) throws IllegalAccessException;
    void deletePropertyRepair(long propertyRepairId);
}