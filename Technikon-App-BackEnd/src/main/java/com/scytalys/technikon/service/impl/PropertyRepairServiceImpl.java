package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.dto.PropertyRepairDto;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.repository.PropertyRepairRepository;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.repository.RepoPropertyRepair;
import com.scytalys.technikon.service.PropertyRepairService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
