package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.dto.repair.*;
import com.scytalys.technikon.mapper.PropertyRepairMapper;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.repository.PropertyRepairRepository;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.service.PropertyOwnerService;
import com.scytalys.technikon.service.PropertyRepairService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.scytalys.technikon.domain.category.RepairStatus.DEFAULT_PENDING;

@Service
@AllArgsConstructor
public class PropertyRepairServiceImpl implements PropertyRepairService {
    private final PropertyRepairRepository propertyRepairRepository;
    private final PropertyOwnerRepository propertyOwnerRepository;
    private final PropertyRepository propertyRepository;
    private final PropertyRepairMapper propertyRepairMapper;
    private final PropertyOwnerService propertyOwnerService;


    /**
     * Creates a new property repair in the repository
     *
     * @return The created property repair.
     */
    @Override
    public PropertyRepairDto createPropertyRepair(PropertyRepairDto propertyRepairDto) {
        validatePropertyOwnerExistsOrThrow(propertyRepairDto.propertyOwnerId());
        validatePropertyExistsOrThrow(propertyRepairDto.propertyId());
        validateDateIsBeforeConstructionOrThrow(propertyRepairDto.propertyId(), propertyRepairDto.dateOfRepair());
        validateDateInputOrThrow(propertyRepairDto.dateOfRepair());
        validateCostInputOrThrow(propertyRepairDto.cost());
        validateShortDescription(propertyRepairDto.shortDescription());
        PropertyRepair converted = propertyRepairMapper.RepairDtoToPropertyRepair(propertyRepairDto);
        propertyRepairRepository.save(converted);
        return propertyRepairMapper.RepairToPropertyRepairDto(converted);
    }


    public PropertyRepairDto getPropertyRepair(long id){
        PropertyRepair propertyRepair = propertyRepairRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Property repair with id "+ id+ " not found"));
        return propertyRepairMapper.RepairToPropertyRepairDto(propertyRepair);
    }

    public List<PropertyRepairDto> getAllPropertyRepairs() {
        return propertyRepairRepository.findAll()
                .stream()
                .map(propertyRepairMapper::RepairToPropertyRepairDto)
                .collect(Collectors.toList());
    }


    /**
     * Searches for all property repairs in the repository that were scheduled by a specific property owner.
     *
     * @return A list of all property repairs that were scheduled by the specified property owner. If no repairs were found for the given owner, an empty list is returned.
     */
    @Override
    public List<PropertyRepairDto> getPropertyRepairsByOwner(long propertyOwnerId) {
        propertyOwnerRepository.findById(propertyOwnerId).orElseThrow(()-> new EntityNotFoundException("Property owner with id "+ propertyOwnerId+ " not found"));
       List<PropertyRepair> propertyRepairs = propertyRepairRepository.getPropertyRepairsByOwner(propertyOwnerId);
        return propertyRepairs
                .stream()
                .map(propertyRepairMapper::RepairToPropertyRepairDto)
                .collect(Collectors.toList());
    }


    /**
     * Searches for (a) property repair(s) in the repository by the date of the scheduled repair.
     *
     * @return The found property repair or null.
     */
    @Override
    public List<PropertyRepairDto> searchPropertyRepairByDate(long propertyOwnerId, LocalDate date) {
        validateDateInputOrThrow(date);
        return propertyRepairRepository.getPropertyRepairByDate(propertyOwnerId, date)
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
    public List<PropertyRepairDto> searchPropertyRepairsByDates (long propertyOwnerId, LocalDate firstDate, LocalDate lastDate) {
        validateDateInputOrThrow(firstDate);
        validateDateInputOrThrow(lastDate);
        validateDatesInputOrThrow(firstDate, lastDate);
        return propertyRepairRepository.getPropertyRepairsByDates(propertyOwnerId, firstDate, lastDate)
                .stream()
                .map(propertyRepairMapper::RepairToPropertyRepairDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PropertyRepairUpdateDto updatePropertyRepair(long id, PropertyRepairUpdateDto dto) {
        PropertyRepair propertyRepair = propertyRepairRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Property repair with id "+ id +" not found"));

        // Apply updates
        if(dto.dateOfRepair() != null){
            validateDateInputOrThrow(dto.dateOfRepair());
            propertyRepair.setDateOfRepair(dto.dateOfRepair());
        }
        if(dto.shortDescription() !=null){
            propertyRepair.setShortDescription(dto.shortDescription());
        }
        if(dto.repairType()!=null){
            propertyRepair.setRepairType(dto.repairType());
        }
        if(dto.repairStatus()!=null){
            propertyRepair.setRepairStatus(dto.repairStatus());
        }
        if (dto.cost() !=null) {
            propertyRepair.setCost(dto.cost());
        }
        if (dto.longDescription() !=null) {
            propertyRepair.setLongDescription(dto.longDescription());
        }
        propertyRepairRepository.save(propertyRepair);
        return propertyRepairMapper.RepairToPropertyRepairUpdateDto(propertyRepair);
    }


    /**
     * Deletes a specific property repair from the repository.
     *
     */
    @Transactional
    public void deletePropertyRepair(long id) {
        PropertyRepair propertyRepair = propertyRepairRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Property repair with id "+ id+ " not found"));
       if (!propertyRepair.getRepairStatus().equals(DEFAULT_PENDING)) {
           throw new DataAccessResourceFailureException("Cannot delete a property repair with this status");
       }
       propertyRepairRepository.deleteById(id);
    }

    // VALIDATIONS

    private void validatePropertyOwnerExistsOrThrow(long propertyOwnerId) {
        propertyOwnerRepository.findById(propertyOwnerId).orElseThrow(() -> new EntityNotFoundException("Property owner with id: " + propertyOwnerId + " not found"));
    }

    private void validatePropertyExistsOrThrow(long propertyId){
        propertyRepository.findById(propertyId).orElseThrow(() -> new EntityNotFoundException("Property with id " + propertyId + " not found"));
    }

    private void validatePropertyRepairExistsOrThrow(long propertyRepairId) {
        propertyRepository.findById(propertyRepairId).orElseThrow(() ->
                new EntityNotFoundException("Property repair with id " + propertyRepairId + " not found"));
    }

    private void validateDateIsBeforeConstructionOrThrow(long propertyId, LocalDate date) {
        if (date.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Date of repair must not be in the past");
        }
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Property not found"));
        if (date.isBefore(property.getConstructionYear())) {
            throw new IllegalArgumentException("Date of repair must not be before the construction year of the property");
        }
    }

    private void validateDateInputOrThrow(LocalDate date) {
        if (date.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Date of repair must not be in the past");
        }
    }

    private void validateDatesInputOrThrow(LocalDate date1, LocalDate date2){
        if (date2.isBefore(date1)){
            throw  new IllegalArgumentException("Invalid range of dates");
        }
    }

    private void validateCostInputOrThrow(BigDecimal cost) {
        if (cost.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cost must be greater than 0");
        }
    }

    private void validateShortDescription(String shortDescription){
        if (!shortDescription.matches(".{0,50}")) {
            throw new IllegalArgumentException("Short description must be 50 characters or less");
        }
    }


}
