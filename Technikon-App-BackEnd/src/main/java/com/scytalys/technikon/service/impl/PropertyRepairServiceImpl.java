package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.dto.PropertyRepairDto;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.repository.PropertyRepairRepository;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.repository.RepoPropertyRepair;
import com.scytalys.technikon.service.PropertyRepairService;
import lombok.AllArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
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
    private final RepoPropertyRepair propertyRepair;

    /**
     * Creates a new property repair in the repository
     *
     * @param propertyRepairDto The property repair to be created.
     * @return The created property repair.
     */
    @Override
    public PropertyRepairDto createPropertyRepair(PropertyRepairDto propertyRepairDto) {
        PropertyRepair propertyRepair = new PropertyRepair();
        propertyRepair.setPropertyOwner(propertyOwnerRepository.findById(propertyRepairDto.propertyOwnerId()).orElseThrow(() -> new NoSuchElementException("PropertyOwner with id " + propertyRepairDto.propertyOwnerId() + " not found")));;
        propertyRepair.setProperty(propertyRepository.findById(propertyRepairDto.propertyId()).orElse(null));

        propertyRepair.setDateOfRepair(propertyRepairDto.dateOfRepair());
        propertyRepair.setShortDescription(propertyRepairDto.shortDescription());
        propertyRepair.setRepairType(propertyRepairDto.repairType());
        propertyRepair.setRepairStatus(propertyRepairDto.repairStatus());
        propertyRepair.setCost(propertyRepairDto.cost());
        propertyRepair.setLongDescription(propertyRepairDto.longDescription());
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
        return propertyRepairRepository.searchPropertyRepairs(propertyOwnerId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Searches for (a) property repair(s) in the repository by the date of the scheduled repair.
     *
     * @param propertyOwnerId The unique identifier of the property owner who scheduled the repair. This is used to filter the repairs that belong to a specific owner.
     * @param date The date of the scheduled repair to be searched.
     * @return The found property repair or null.
     *
     */
    @Override
    public List<PropertyRepairDto> searchPropertyRepairByDate(long propertyOwnerId, LocalDate date) {
        return propertyRepairRepository.searchPropertyRepairByDate(propertyOwnerId, date)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Searches for (a) property repair(s) in the repository within a specified date range. The date range represents the scheduled dates of the repairs.
     *
     * @param propertyOwnerId The unique identifier of the property owner who scheduled the repair. This is used to filter the repairs that belong to a specific owner.
     * @param firstDate The start date of the range within which the scheduled repair dates are searched. It is inclusive, meaning repairs scheduled on this date will be included in the results.
     * @param lastDate The end date of the range within which the scheduled repair dates are searched. It is inclusive, meaning repairs scheduled on this date will be included in the results.
     * @return A list of property repairs that fall within the specified date range and were scheduled by the specified property owner. If no repairs match the criteria, an empty list is returned.
     */
    @Override
    public List<PropertyRepairDto> searchPropertyRepairByDates(long propertyOwnerId, LocalDate firstDate, LocalDate lastDate ) {
        return propertyRepairRepository.searchPropertyRepairByDates(propertyOwnerId, firstDate, lastDate)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Override
    public String updatePropertyRepair(PropertyRepairDto propertyRepairDto, long propertyRepairId) throws IllegalAccessException {
        PropertyRepair propertyRepair1 = new PropertyRepair();
        propertyRepair1.setDateOfRepair(propertyRepairDto.dateOfRepair());
        propertyRepair1.setRepairType(propertyRepairDto.repairType());
        propertyRepair1.setCost(propertyRepairDto.cost());
        propertyRepair1.setRepairStatus(propertyRepairDto.repairStatus());
        propertyRepair1.setLongDescription(propertyRepairDto.longDescription());
        propertyRepair1.setShortDescription(propertyRepairDto.shortDescription());
        propertyRepair.update(
                propertyRepair1,
                propertyRepairId);
        return "Updated property repair";
    }

    public void deletePropertyRepair(long propertyRepairId){
        propertyRepairRepository.deleteById(propertyRepairId);
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




    //-------------------------------------

//    /**
//     * Creates a new property repair in the repository
//     *
//     * @param propertyRepairDto The property repair to be created.
//     * @return The created property repair.
//     */
//    @Override
//    public PropertyRepairDto createPropertyRepair(PropertyRepairDto propertyRepairDto) {
//        PropertyRepair propertyRepair = new PropertyRepair();
//        propertyRepair.setPropertyOwner(propertyOwnerRepository.findById(propertyRepairDto.propertyOwnerId()).orElseThrow(() -> new NoSuchElementException("PropertyOwner with id " + propertyRepairDto.propertyOwnerId() + " not found")));;
//        propertyRepair.setProperty(propertyRepository.findById(propertyRepairDto.propertyId()).orElse(null));
//
//        propertyRepair.setDateOfRepair(propertyRepairDto.dateOfRepair());
//        propertyRepair.setShortDescription(propertyRepairDto.shortDescription());
//        propertyRepair.setRepairType(propertyRepairDto.repairType());
//        propertyRepair.setRepairStatus(propertyRepairDto.repairStatus());
//        propertyRepair.setCost(propertyRepairDto.cost());
//        propertyRepair.setLongDescription(propertyRepairDto.longDescription());
//        PropertyRepair repair = propertyRepairRepository.save(propertyRepair);
//        return convertToDto(repair);
//    }



//    /**
//     * Deletes a specific property repair from the repository.
//     *
//     * @param propertyOwnerId The unique identifier of the property owner who scheduled the repair.
//     * @param propertyRepairId The unique identifier of the property repair to be deleted.
//     */
//    @Transactional
//    public int deletePropertyRepair(long propertyOwnerId, long propertyRepairId) {
//        return propertyRepairRepository.deletePropertyRepair(propertyOwnerId, propertyRepairId);
//    }


    private PropertyRepair validatePropertyRepairExists(long propertyRepairId) {
        return propertyRepairRepository.findById(propertyRepairId)
                .orElseThrow(() -> new OpenApiResourceNotFoundException("PropertyRepair not found with id: " + propertyRepairId));
    }

    private void validatePropertyRepairMatchesPropertyOwner(PropertyRepair propertyRepair, long propertyOwnerId) {
        if (propertyRepair.getPropertyOwner().getId() != propertyOwnerId) {
            throw new RuntimeException("PropertyRepair does not belong to PropertyOwner with id: " + propertyOwnerId);
        }
    }


}
