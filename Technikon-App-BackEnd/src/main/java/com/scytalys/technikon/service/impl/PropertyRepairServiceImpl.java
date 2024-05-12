package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.dto.repair.*;
import com.scytalys.technikon.exception.InvalidInputException;
import com.scytalys.technikon.mapper.PropertyRepairMapper;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.repository.PropertyRepairRepository;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.service.PropertyRepairService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PropertyRepairServiceImpl implements PropertyRepairService {

    private final PropertyRepairRepository propertyRepairRepository;
    private final PropertyOwnerRepository propertyOwnerRepository;
    private final PropertyRepository propertyRepository;
    private final PropertyRepairMapper propertyRepairMapper;


    /**
     * Creates a new property repair in the repository
     *
     * @return The created property repair.
     */
    @Override
    public PropertyRepairDto createPropertyRepair(PropertyRepairDto propertyRepairDto) {
//        validatePropertyOwnerExistsOrThrow(propertyRepairCreationDto.propertyOwnerId());
//        validatePropertyExistsOrThrow(propertyRepairCreationDto.propertyId());
//        validateDateInput(propertyRepairCreationDto.dateOfRepair());
        PropertyRepair converted = propertyRepairMapper.RepairDtoToPropertyRepair(propertyRepairDto);
        propertyRepairRepository.save(converted);
        return propertyRepairMapper.RepairToPropertyRepairDto(converted);
    }

    @Override
    public PropertyRepairDto searchPropertyRepair(long propertyOwnerId, long propertyId, long repairId){
        PropertyRepair propertyRepair = propertyRepairRepository.getPropertyRepair(propertyOwnerId, propertyId, repairId);
        return propertyRepairMapper.RepairToPropertyRepairDto(propertyRepair);
    }


    /**
     * Searches for all property repairs in the repository that were scheduled by a specific property owner.
     *
     * @return A list of all property repairs that were scheduled by the specified property owner. If no repairs were found for the given owner, an empty list is returned.
     */
    @Override
    public List<PropertyRepairDto> searchPropertyRepairs(long propertyOwnerId, long propertyId) {
        return propertyRepairRepository.getPropertyRepairs(propertyOwnerId, propertyId).stream()
                .map(propertyRepairMapper::RepairToPropertyRepairDto)
                .collect(Collectors.toList());
    }


    /**
     * Searches for (a) property repair(s) in the repository by the date of the scheduled repair.
     *
     * @return The found property repair or null.
     */
    @Override
    public List<PropertyRepairDto> searchPropertyRepairByDate(PropertyRepairSearchByDateDto propertyRepairSearchByDateDto) {
        return propertyRepairRepository.getPropertyRepairByDate(propertyRepairSearchByDateDto.propertyOwnerId(), propertyRepairSearchByDateDto.propertyId(), propertyRepairSearchByDateDto.dateOfRepair())
                .stream()
                .map(propertyRepairMapper::RepairToPropertyRepairDto)
                .collect(Collectors.toList());
    }

    /**
     * Searches for (a) property repair(s) in the repository within a specified date range. The date range represents the scheduled dates of the repairs.
     *
     * @return A list of property repairs that fall within the specified date range and were scheduled by the specified property owner. If no repairs match the criteria, an empty list is returned.
     */
    @Override
    public List<PropertyRepairDto> searchPropertyRepairByDates (PropertyRepairSearchByDatesDto propertyRepairSearchByDatesDto) {
        return propertyRepairRepository.getPropertyRepairByDates(propertyRepairSearchByDatesDto.propertyOwnerId(), propertyRepairSearchByDatesDto.propertyId(), propertyRepairSearchByDatesDto.firstDate(), propertyRepairSearchByDatesDto.lastDate())
                .stream()
                .map(propertyRepairMapper::RepairToPropertyRepairDto)
                .collect(Collectors.toList());
    }


    /**
     * Updates the date of a specific property repair in the repository.
     *
     * @return The number of rows affected in the database.
     */
    @Override
    public int updatePropertyRepairByDate(PropertyRepairUpdateByDateDto propertyRepairUpdateByDateDto) {
        return propertyRepairRepository.updatePropertyRepairByDate(propertyRepairUpdateByDateDto.id(), propertyRepairUpdateByDateDto.propertyOwnerId(), propertyRepairUpdateByDateDto.propertyId(), propertyRepairUpdateByDateDto.dateOfRepair());
    }


    /**
     * Updates the short description of a specific property repair in the repository.
     *
     * @return The number of rows affected in the database.
     */
    @Override
    public int updatePropertyRepairByShortDescription(PropertyRepairUpdateByShortDescriptionDto dto) {
        return propertyRepairRepository.updatePropertyRepairByShortDescription(dto.id(), dto.propertyOwnerId(), dto.propertyId(), dto.shortDescription());
    }


    /**
     * Updates the repair type of a specific property repair in the repository.
     *
     *
     * @return The number of rows affected in the database.
     */
    @Override
    public int updatePropertyRepairByType(PropertyRepairUpdateByTypeDto dto) {
        return propertyRepairRepository.updatePropertyRepairByType(dto.id(), dto.propertyOwnerId(), dto.propertyId(), dto.repairType());
    }

    /**
     * Updates the cost of a specific property repair in the repository.
     *
     *
     * @return The number of rows affected in the database.
     */

    @Override
    public int updatePropertyRepairByCost(PropertyRepairUpdateByCostDto dto) {
        return propertyRepairRepository.updatePropertyRepairByCost(dto.id(), dto.propertyOwnerId(), dto.propertyId(), dto.cost());
    }


    /**
     * Updates the long description of a specific property repair in the repository.
     *
     *
     * @return The number of rows affected in the database.
     */
    @Override
    public int updatePropertyRepairByLongDescription(PropertyRepairUpdateByLongDescriptionDto dto) {
        return propertyRepairRepository.updatePropertyRepairByLongDescription(dto.id(), dto.propertyOwnerId(), dto.propertyId(), dto.longDescription());
    }


    /**
     * Deletes a specific property repair from the repository.
     *
     * @param propertyOwnerId  The unique identifier of the property owner who scheduled the repair.
     *
     */
    @Transactional
    public void deletePropertyRepair(long propertyOwnerId, long propertyId, long id) {
        int deletedRows = propertyRepairRepository.deletePropertyRepair(propertyOwnerId, propertyId, id);
        if (deletedRows == 0) {
            throw new DataAccessResourceFailureException("Failed to delete property repair with id: " + id);
        }
    }


    private void validatePropertyOwnerExistsOrThrow(long propertyOwnerId) {
        propertyOwnerRepository.findById(propertyOwnerId)
                .orElseThrow(() -> new NoSuchElementException("Property owner with id: " + propertyOwnerId + " not found"));
    }

    private void validatePropertyExistsOrThrow(long propertyId){
        propertyRepository.findById(propertyId).orElseThrow(() ->
                new NoSuchElementException("Property with id " + propertyId + " not found"));
    }

    private void validatePropertyRepairExistsOrThrow(long propertyRepairId) {
        propertyRepository.findById(propertyRepairId).orElseThrow(() ->
                new NoSuchElementException("Property repair with id " + propertyRepairId + " not found"));
    }


    private void validateDateInput(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new InvalidInputException("Date of repair must not be in the past");
        }

    }
}
