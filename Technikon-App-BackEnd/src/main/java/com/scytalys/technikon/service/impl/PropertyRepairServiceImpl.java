package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.dto.repair.*;
import com.scytalys.technikon.mapper.PropertyRepairMapper;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.repository.PropertyRepairRepository;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.service.PropertyRepairService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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


    /**
     * Creates a new property repair in the repository.
     *
     * @param propertyRepairDto The DTO of the property repair to be created.
     * @return The created property repair DTO.
     */
    @Override
    @CacheEvict(value = "PropertyRepairs", allEntries = true)
    public PropertyRepairDto createPropertyRepair(PropertyRepairDto propertyRepairDto) {
        validatePropertyOwnerExistsOrThrow(propertyRepairDto.propertyOwnerId());
        validatePropertyExistsOrThrow(propertyRepairDto.propertyId());
        validateDateInputOrThrow(propertyRepairDto.dateOfRepair());
        validateCostInputOrThrow(propertyRepairDto.cost());
        validateShortDescription(propertyRepairDto.shortDescription());
        PropertyRepair converted = propertyRepairMapper.RepairDtoToPropertyRepair(propertyRepairDto);
        PropertyRepair newPropertyRepair = propertyRepairRepository.save(converted);
        return propertyRepairMapper.RepairToPropertyRepairDto(newPropertyRepair);
    }


    /**
     * Retrieves a property repair by its ID.
     *
     * @param id The ID of the property repair to retrieve.
     * @return The property repair DTO.
     */
    public PropertyRepairDto getPropertyRepair(long id){
        PropertyRepair propertyRepair = validatePropertyRepairExistsOrThrow(id);
        return propertyRepairMapper.RepairToPropertyRepairDto(propertyRepair);
    }


    /**
     * Retrieves all property repairs.
     *
     * @return A list of all property repair DTOs.
     */
    @Cacheable("PropertyRepairs")
    public List<PropertyRepairDto> getAllPropertyRepairs() {
        return propertyRepairRepository.findAll()
                .stream()
                .map(propertyRepairMapper::RepairToPropertyRepairDto)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves all property repairs scheduled by a specific property owner.
     *
     * @param propertyOwnerId The ID of the property owner.
     * @return A list of all property repairs that were scheduled by the specified property owner. If no repairs were found for the given owner, an empty list is returned.
     */
    @Override
    @Cacheable("PropertyRepairs")
    public List<PropertyRepairDto> getPropertyRepairsByOwner(long propertyOwnerId) {
        propertyOwnerRepository.findById(propertyOwnerId).orElseThrow(()-> new EntityNotFoundException("Property owner with id "+ propertyOwnerId+ " not found"));
       List<PropertyRepair> propertyRepairs = propertyRepairRepository.getPropertyRepairsByOwner(propertyOwnerId);
        return propertyRepairs
                .stream()
                .map(propertyRepairMapper::RepairToPropertyRepairDto)
                .collect(Collectors.toList());
    }


    /**
     * Searches for property repairs in the repository by the date of the scheduled repair.
     *
     * @param propertyOwnerId The ID of the property owner.
     * @param date The date of the scheduled repair.
     * @return A list of property repair DTOs or an empty list if no repairs match the criteria.
     */
    @Override
    @Cacheable("PropertyRepairs")
    public List<PropertyRepairDto> searchPropertyRepairsByDate(long propertyOwnerId, LocalDate date) {
        validateDateInputOrThrow(date);
        return propertyRepairRepository.getPropertyRepairByDate(propertyOwnerId, date)
                .stream()
                .map(propertyRepairMapper::RepairToPropertyRepairDto)
                .collect(Collectors.toList());
    }


    /**
     * Searches for property repairs in the repository within a specified date range. The date range represents the scheduled dates of the repairs.
     *
     * @param propertyOwnerId The ID of the property owner.
     * @param firstDate The start date of the range.
     * @param lastDate The end date of the range.
     * @return A list of property repairs that fall within the specified date range and were scheduled by the specified property owner. If no repairs match the criteria, an empty list is returned.
     */
    @Override
    @Cacheable("PropertyRepairs")
    public List<PropertyRepairDto> searchPropertyRepairsByDates (long propertyOwnerId, LocalDate firstDate, LocalDate lastDate) {
        validateDateInputOrThrow(firstDate);
        validateDateInputOrThrow(lastDate);
        validateDatesInputOrThrow(firstDate, lastDate);
        return propertyRepairRepository.getPropertyRepairsByDates(propertyOwnerId, firstDate, lastDate)
                .stream()
                .map(propertyRepairMapper::RepairToPropertyRepairDto)
                .collect(Collectors.toList());
    }


    /**
     * Updates a specific property repair.
     *
     * @param id The ID of the property repair to update.
     * @param dto The DTO containing the updated property repair data.
     * @return The updated property repair DTO.
     */
    @Transactional
    @Override
    @CacheEvict(value = "PropertyRepairs", allEntries = true)
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
            validateCostInputOrThrow(dto.cost());
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
     * @param id The ID of the property repair to delete.
     */
    @Transactional
    @CacheEvict(value = "PropertyRepairs", allEntries = true)
    public void deletePropertyRepair(long id) {
        PropertyRepair propertyRepair = propertyRepairRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Property repair with id "+ id+ " not found"));
       if (!propertyRepair.getRepairStatus().equals(DEFAULT_PENDING)) {
           throw new DataAccessResourceFailureException("Cannot delete a property repair with this status");
       }
       propertyRepairRepository.deleteById(id);
    }


    // VALIDATIONS

    /**
     * Validates if a property owner exists in the repository.
     *
     * @param propertyOwnerId The ID of the property owner.
     * @throws EntityNotFoundException if the property owner does not exist.
     */
    private void validatePropertyOwnerExistsOrThrow(long propertyOwnerId) {
        propertyOwnerRepository.findById(propertyOwnerId).orElseThrow(() -> new EntityNotFoundException("Property owner with id: " + propertyOwnerId + " not found"));
    }


    /**
     * Validates if a property exists in the repository.
     *
     * @param propertyId The ID of the property.
     * @throws EntityNotFoundException if the property does not exist.
     */
    private void validatePropertyExistsOrThrow(long propertyId){
        propertyRepository.findById(propertyId).orElseThrow(() -> new EntityNotFoundException("Property with id " + propertyId + " not found"));
    }


    /**
     * Validates if a property repair exists in the repository.
     *
     * @param propertyRepairId The ID of the property repair.
     * @throws EntityNotFoundException if the property repair does not exist.
     */
    private PropertyRepair validatePropertyRepairExistsOrThrow(long propertyRepairId) {
        return propertyRepairRepository.findById(propertyRepairId).orElseThrow(() ->
                new EntityNotFoundException("Property repair with id " + propertyRepairId + " not found"));
    }


    /**
     * Validates if the date of repair is not in the past.
     *
     * @param date The date of the repair.
     * @throws IllegalArgumentException if the date of repair is in the past.
     */
    private void validateDateInputOrThrow(LocalDate date) {
        if (date.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Date of repair must not be in the past");
        }
    }


    /**
     * Validates if the second date is not before the first date.
     *
     * @param date1 The first date.
     * @param date2 The second date.
     * @throws IllegalArgumentException if the second date is before the first date.
     */
    private void validateDatesInputOrThrow(LocalDate date1, LocalDate date2){
        if (date2.isBefore(date1)){
            throw  new IllegalArgumentException("Invalid range of dates");
        }
    }


    /**
     * Validates if the cost is greater than 0.
     *
     * @param cost The cost to validate.
     * @throws IllegalArgumentException if the cost is less than or equal to 0.
     */
    private void validateCostInputOrThrow(BigDecimal cost) {
        if (cost.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cost must be greater than 0");
        }
    }


    /**
     * Validates if the short description is 50 characters or less.
     *
     * @param shortDescription The short description to validate.
     * @throws IllegalArgumentException if the short description is more than 50 characters.
     */
    private void validateShortDescription(String shortDescription){
        if (!shortDescription.matches(".{0,50}")) {
            throw new IllegalArgumentException("Short description must be 50 characters or less");
        }
    }


}
