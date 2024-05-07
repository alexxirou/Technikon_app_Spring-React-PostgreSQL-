package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.dto.PropertyRepairDto;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.repository.PropertyRepairRepository;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.repository.impl.PropertyRepairRepoImpl;
import com.scytalys.technikon.service.PropertyRepairService;
import lombok.AllArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PropertyRepairRepoImpl propertyRepairRepoImpl;


    /**
     * Creates a new property repair in the repository
     *
     * @param propertyRepairDto The property repair to be created.
     * @return The created property repair.
     */
    @Override
    public PropertyRepairDto createPropertyRepair(PropertyRepairDto propertyRepairDto) {
        PropertyOwner propertyOwner = propertyOwnerRepository.findById(propertyRepairDto.propertyOwnerId()).orElseThrow(() ->
                new NoSuchElementException("PropertyOwner with id " + propertyRepairDto.propertyOwnerId() + " not found"));
        Property property = propertyRepository.findById(propertyRepairDto.propertyId()).orElseThrow(() ->
                new NoSuchElementException("Property with id " + propertyRepairDto.propertyId() + " not found"));
        PropertyRepair propertyRepair = convertToPropertyRepair(propertyRepairDto, propertyOwner, property);
        PropertyRepair repair = propertyRepairRepository.save(propertyRepair);
        return convertToDto(repair);
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
        validatePropertyOwnerExistsOrThrow(propertyOwnerId);
        List<PropertyRepair> foundRepairs = propertyRepairRepository.searchPropertyRepairByDates(propertyOwnerId, firstDate, lastDate);
        if (foundRepairs.isEmpty()) {
            return Collections.emptyList();
        }
        return foundRepairs
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Override
    public void updatePropertyRepair(long propertyOwnerId, long propertyRepairId, PropertyRepairDto propertyRepairDto) {
        validatePropertyOwnerExistsOrThrow(propertyOwnerId);
        int updatedPropertyRepairs = propertyRepairRepoImpl.updatePropertyRepairExistingFields(convertToPropertyRepair(propertyRepairDto), propertyRepairId);
        if (updatedPropertyRepairs == 0) {
            throw new DataAccessResourceFailureException("Property repair with id" + propertyRepairId + " does not exist");
        }
    }

    /**
     * Deletes a specific property repair from the repository.
     *
     * @param propertyOwnerId  The unique identifier of the property owner who scheduled the repair.
     * @param propertyRepairId The unique identifier of the property repair to be deleted.
     */
    @Transactional
    public void deletePropertyRepair(long propertyOwnerId, long propertyRepairId) {
        validatePropertyOwnerExistsOrThrow(propertyOwnerId);
        int deletedRows = propertyRepairRepository.deletePropertyRepair(propertyOwnerId, propertyRepairId);
        if (deletedRows == 0) {
            throw new DataAccessResourceFailureException("Failed to delete property repair for property owner with id: " + propertyOwnerId);
        }
    }

    private PropertyRepairDto convertToDto(PropertyRepair propertyRepair) {
        return new PropertyRepairDto(
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
        if (propertyOwnerRepository.findById(propertyOwnerId).isEmpty()) {
            throw new DataAccessResourceFailureException("Property owner with id: " + propertyOwnerId + " not found");
        }
    }
}
