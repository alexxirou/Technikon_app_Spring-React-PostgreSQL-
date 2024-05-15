package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.dto.repair.*;
import com.scytalys.technikon.exception.InvalidInputException;
import com.scytalys.technikon.mapper.PropertyRepairMapper;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.repository.PropertyRepairRepository;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.service.PropertyRepairService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
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


    public PropertyRepairDto getPropertyRepair(long id){
        PropertyRepair propertyRepair = propertyRepairRepository.findById(id).orElseThrow();
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
        return propertyRepairRepository.getPropertyRepairsByOwner(propertyOwnerId)
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
    public List<PropertyRepairDto> searchPropertyRepairByDate(PropertyRepairSearchByDateDto propertyRepairSearchByDateDto) {
        return propertyRepairRepository.getPropertyRepairByDate(propertyRepairSearchByDateDto.propertyOwnerId(), propertyRepairSearchByDateDto.dateOfRepair())
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
        return propertyRepairRepository.getPropertyRepairByDates(propertyRepairSearchByDatesDto.propertyOwnerId(), propertyRepairSearchByDatesDto.firstDate(), propertyRepairSearchByDatesDto.lastDate())
                .stream()
                .map(propertyRepairMapper::RepairToPropertyRepairDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PropertyRepairUpdateDto updatePropertyRepair(long id, PropertyRepairUpdateDto dto) {
        PropertyRepair propertyRepair = propertyRepairRepository.findById(id).orElse(null);
        if (propertyRepair == null) {
            return null;
        }

        // Apply updates
        if(dto.dateOfRepair() != null){
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
     *
     *
     */
    @Transactional
    public void deletePropertyRepair(long id) {
        PropertyRepair propertyRepair = propertyRepairRepository.findById(id).orElseThrow(EntityNotFoundException::new);
       if (propertyRepair.getRepairStatus().equals(DEFAULT_PENDING)){
            int deletedRows = propertyRepairRepository.deletePropertyRepair(id);
            if (deletedRows == 0) {
                throw new DataAccessResourceFailureException("Failed to delete property repair with id: " + id);
            }
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
