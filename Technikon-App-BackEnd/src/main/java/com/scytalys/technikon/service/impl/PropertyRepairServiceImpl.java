package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.dto.PropertyRepairCreationDto;
import com.scytalys.technikon.dto.PropertyRepairDto;
import com.scytalys.technikon.exception.InvalidInputException;
import com.scytalys.technikon.exception.ResourceNotFoundException;
import com.scytalys.technikon.mapper.PropertyRepairMapper;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.repository.PropertyRepairRepository;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.service.PropertyRepairService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
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
     * @param propertyRepairCreationDto The property repair to be created.
     * @return The created property repair.
     */
    @Override
    public PropertyRepairCreationDto createPropertyRepair(PropertyRepairCreationDto propertyRepairCreationDto) {
        validatePropertyOwnerExistsOrThrow(propertyRepairCreationDto.propertyOwnerId());
        validatePropertyExistsOrThrow(propertyRepairCreationDto.propertyId());
        validateDateInput(propertyRepairCreationDto.dateOfRepair());
        PropertyRepair convertedToPropertyRepair = propertyRepairMapper.convertPropertyRepairCreationDtoToPropertyRepair(propertyRepairCreationDto);
        propertyRepairRepository.save(convertedToPropertyRepair);
        return propertyRepairMapper.convertPropertyRepairToPropertyRepairCreationDto(convertedToPropertyRepair);
    }


    /**
     * Searches for all property repairs in the repository that were scheduled by a specific property owner.
     *
     * @param propertyOwnerId The unique identifier of the property owner. This is used to filter the repairs that belong to this owner.
     * @return A list of all property repairs that were scheduled by the specified property owner. If no repairs were found for the given owner, an empty list is returned.
     */
    @Override
    public List<PropertyRepairDto> searchPropertyRepairs(long propertyOwnerId) {
        validatePropertyOwnerExistsOrThrow(propertyOwnerId);
        List<PropertyRepair> foundRepairs = propertyRepairRepository.searchPropertyRepairs(propertyOwnerId);
        if (foundRepairs.isEmpty()) {
            return Collections.emptyList();
        }
        return foundRepairs
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    /**
     * Searches for (a) property repair(s) in the repository by the date of the scheduled repair.
     *
     * @param propertyOwnerId The unique identifier of the property owner who scheduled the repair. This is used to filter the repairs that belong to a specific owner.
     * @param date            The date of the scheduled repair to be searched.
     * @return The found property repair or null.
     */
    @Override
    public List<PropertyRepairDto> searchPropertyRepairByDate(long propertyOwnerId, LocalDate date) {
        validatePropertyOwnerExistsOrThrow(propertyOwnerId);
        List<PropertyRepair> foundRepairs = propertyRepairRepository.searchPropertyRepairByDate(propertyOwnerId, date);
        if (foundRepairs.isEmpty()) {
            return Collections.emptyList();
        }
        return foundRepairs
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Searches for (a) property repair(s) in the repository within a specified date range. The date range represents the scheduled dates of the repairs.
     *
     * @param propertyOwnerId The unique identifier of the property owner who scheduled the repair. This is used to filter the repairs that belong to a specific owner.
     * @param firstDate       The start date of the range within which the scheduled repair dates are searched. It is inclusive, meaning repairs scheduled on this date will be included in the results.
     * @param lastDate        The end date of the range within which the scheduled repair dates are searched. It is inclusive, meaning repairs scheduled on this date will be included in the results.
     * @return A list of property repairs that fall within the specified date range and were scheduled by the specified property owner. If no repairs match the criteria, an empty list is returned.
     */
    @Override
    public List<PropertyRepairDto> searchPropertyRepairByDates(long propertyOwnerId, LocalDate firstDate, LocalDate lastDate) {
        List<PropertyRepair> foundRepairs = propertyRepairRepository.searchPropertyRepairByDates(propertyOwnerId, firstDate, lastDate);

        return foundRepairs
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PropertyRepairCreationDto patchPropertyRepair(long propertyOwnerId,long propertyRepairId, PropertyRepairCreationDto propertyRepairCreationDto) {
        PropertyRepair propertyRepair = propertyRepairRepository.findById(propertyRepairId).orElseThrow(() -> new ResourceNotFoundException("PropertyRepair not found"));
        propertyRepairMapper.update(propertyRepairCreationDto, propertyRepair);
        PropertyRepair updatedPropertyRepair = propertyRepairRepository.save(propertyRepair);
        return propertyRepairMapper.convertPropertyRepairToPropertyRepairCreationDto(updatedPropertyRepair);
    }


//    /**
//     * Updates the date of a specific property repair in the repository.
//     *
//     * @param propertyRepairId The unique identifier of the property repair to be updated.
//     * @param date The new date for the property repair.
//     * @return The number of rows affected in the database.
//     */
//    @Override
//    public int updatePropertyRepairByDate(long propertyRepairId, LocalDate date) {
//        return propertyRepairRepository.updatePropertyRepairByDate(propertyRepairId, date);
//    }
//
//
//    /**
//     * Updates the short description of a specific property repair in the repository.
//     *
//     * @param propertyRepairId The unique identifier of the property repair to be updated.
//     * @param shortDescription The new short description for the property repair.
//     * @return The number of rows affected in the database.
//     */
//    @Override
//    public int updatePropertyRepairByShortDescription(long propertyRepairId, String shortDescription) {
//        return propertyRepairRepository.updatePropertyRepairByShortDescription(propertyRepairId, shortDescription);
//    }
//
//
//    /**
//     * Updates the repair type of a specific property repair in the repository.
//     *
//     * @param propertyRepairId The unique identifier of the property repair to be updated.
//     * @param repairType The new repair type for the property repair.
//     * @return The number of rows affected in the database.
//     */
//    @Override
//    public int updatePropertyRepairByRepairType(long propertyRepairId, RepairType repairType) {
//        return propertyRepairRepository.updatePropertyRepairByRepairType(propertyRepairId, repairType);
//    }
//
//    /**
//     * Updates the cost of a specific property repair in the repository.
//     *
//     * @param propertyRepairId The unique identifier of the property repair to be updated.
//     * @param cost The new cost for the property repair.
//     * @return The number of rows affected in the database.
//     */
//
//    @Override
//    public int updatePropertyRepairByCost(long propertyRepairId, BigDecimal cost) {
//        return propertyRepairRepository.updatePropertyRepairByCost(propertyRepairId, cost);
//    }
//
//
//    /**
//     * Updates the long description of a specific property repair in the repository.
//     *
//     * @param propertyRepairId The unique identifier of the property repair to be updated.
//     * @param longDescription The new long description for the property repair.
//     * @return The number of rows affected in the database.
//     */
//    @Override
//    public int updatePropertyRepairByLongDescription(long propertyRepairId, String longDescription) {
//        return propertyRepairRepository.updatePropertyRepairByLongDescription(propertyRepairId, longDescription);
//    }









//    @Override
//    public void softDeletePropertyRepair(long propertyOwnerId, long propertyRepairId) {
//        validatePropertyOwnerExistsOrThrow(propertyOwnerId);
//        return propertyRepairRepository.softDeletePropertyRepair(propertyOwnerId, propertyRepairId);
//    }

    /**
     * Deletes a specific property repair from the repository.
     *
     * @param propertyOwnerId  The unique identifier of the property owner who scheduled the repair.
     * @param propertyRepairId The unique identifier of the property repair to be deleted.
     */
    @Transactional
    public void deletePropertyRepair(long propertyOwnerId, long propertyRepairId) {
        int deletedRows = propertyRepairRepository.deletePropertyRepair(propertyOwnerId, propertyRepairId);
        if (deletedRows == 0) {
            throw new DataAccessResourceFailureException("Failed to delete property repair for property owner with id: " + propertyOwnerId);
        }
    }

    private PropertyRepairDto convertToDto(PropertyRepair propertyRepair) {
        return new PropertyRepairDto(
                propertyRepair.getId(),
                propertyRepair.getPropertyOwner().getId(),
                propertyRepair.getProperty().getId(),
                propertyRepair.getDateOfRepair(),
                propertyRepair.getShortDescription(),
                propertyRepair.getRepairType(),
                propertyRepair.getRepairStatus(),
                propertyRepair.getCost(),
                propertyRepair.getLongDescription()
        );
    }

    private PropertyRepair convertToPropertyRepair(PropertyRepairDto propertyRepairDto) {
        PropertyRepair propertyRepair = new PropertyRepair();
        propertyRepair.setDateOfRepair(propertyRepairDto.dateOfRepair());
        propertyRepair.setRepairType(propertyRepairDto.repairType());
        propertyRepair.setCost(propertyRepairDto.cost());
        propertyRepair.setRepairStatus(propertyRepairDto.repairStatus());
        propertyRepair.setLongDescription(propertyRepairDto.longDescription());
        propertyRepair.setShortDescription(propertyRepairDto.shortDescription());
        return propertyRepair;
    }

    private PropertyRepair convertToPropertyRepair(PropertyRepairDto propertyRepairDto, PropertyOwner propertyOwner, Property property) {
        PropertyRepair propertyRepair = new PropertyRepair();
        propertyRepair.setPropertyOwner(propertyOwner);
        propertyRepair.setProperty(property);
        propertyRepair.setDateOfRepair(propertyRepairDto.dateOfRepair());
        propertyRepair.setShortDescription(propertyRepairDto.shortDescription());
        propertyRepair.setRepairType(propertyRepairDto.repairType());
        propertyRepair.setRepairStatus(propertyRepairDto.repairStatus());
        propertyRepair.setCost(propertyRepairDto.cost());
        propertyRepair.setLongDescription(propertyRepairDto.longDescription());
        return propertyRepair;
    }

    private void validatePropertyOwnerExistsOrThrow(long propertyOwnerId) {
        propertyOwnerRepository.findById(propertyOwnerId)
                .orElseThrow(() -> new NoSuchElementException("Property owner with id: " + propertyOwnerId + " not found"));
    }

    private void validatePropertyExistsOrThrow(long propertyId){
        propertyRepository.findById(propertyId).orElseThrow(() ->
                new NoSuchElementException("Property with id " + propertyId + " not found"));
    }

    private void validateDateInput(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new InvalidInputException("Date of repair must not be in the past");
        }

    }
}
