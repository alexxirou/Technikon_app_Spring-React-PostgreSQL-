package com.scytalys.technikon.service;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.dto.repair.*;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;

public interface PropertyRepairService {
    PropertyRepairDto createPropertyRepair(PropertyRepairDto propertyRepairDto);
    PropertyRepairDto getPropertyRepair(long repairId);
    List<PropertyRepairDto> getAllPropertyRepairs();
    List<PropertyRepairDto> getPropertyRepairsByOwner(long propertyOwnerId);
    List<PropertyRepairDto> searchPropertyRepairsByDate(long propertyOwnerId, LocalDate date);
    List<PropertyRepairDto> searchPropertyRepairsByDates(long propertyOwnerId, LocalDate firstDate, LocalDate lastDate);
    PropertyRepairUpdateDto updatePropertyRepair(long id, PropertyRepairUpdateDto propertyRepairUpdateDto, long propertyOwnerId, String role);
    void deletePropertyRepair(long propertyOwnerId, long id, String role);
}