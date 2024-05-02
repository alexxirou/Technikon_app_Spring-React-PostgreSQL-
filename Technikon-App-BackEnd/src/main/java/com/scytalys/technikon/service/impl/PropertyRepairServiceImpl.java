package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.dto.PropertyRepairDto;
import com.scytalys.technikon.repository.PropertyRepairRepository;
import com.scytalys.technikon.service.PropertyRepairService;
import com.scytalys.technikon.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PropertyRepairServiceImpl implements PropertyRepairService {

    private final PropertyRepairRepository propertyRepairRepository;

    @Override
    public PropertyRepair createPropertyRepair(PropertyRepair pr) {
        PropertyRepair propertyRepair = new PropertyRepair();
        propertyRepair.setDateOfRepair(pr.getDateOfRepair());
        propertyRepair.setShortDescription(pr.getShortDescription());
        propertyRepair.setRepairType(pr.getRepairType());
        propertyRepair.setRepairStatus(pr.getRepairStatus());
        propertyRepair.setCost(pr.getCost());
        propertyRepair.setLongDescription(pr.getLongDescription());
        propertyRepairRepository.save(propertyRepair);
        return propertyRepair;
    }

    @Override
    public List<PropertyRepair> getPropertyRepairByDate(LocalDate date) {
        return propertyRepairRepository.getPropertyRepairByDate(date);
    }

    @Override
    public List<PropertyRepair> getPropertyRepairByDates(LocalDate firstDate, LocalDate lastDate ) {
        return propertyRepairRepository.getPropertyRepairByDates(firstDate, lastDate);
    }

    @Override
    public List<PropertyRepair> getPropertyRepairs(long propertyOwnerId) {
        return propertyRepairRepository.getPropertyRepairs(propertyOwnerId);
    }

    @Override
    public boolean updatePropertyRepairByDate(long propertyRepairId, LocalDate date) {
        return propertyRepairRepository.updatePropertyRepairByDate(propertyRepairId, date);
    }

    @Override
    public boolean updatePropertyRepairByShortDescription(long propertyRepairId, String shortDescription) {
        return propertyRepairRepository.updatePropertyRepairByShortDescription(propertyRepairId, shortDescription);
    }

    @Override
    public boolean updatePropertyRepairByRepairType(long propertyRepairId, RepairType repairType) {
        return propertyRepairRepository.updatePropertyRepairByRepairType(propertyRepairId, repairType);
    }

    @Override
    public boolean updatePropertyRepairByCost(long propertyRepairId, double cost) {
        return propertyRepairRepository.updatePropertyRepairByCost(propertyRepairId, cost);
    }

    @Override
    public boolean updatePropertyRepairByLongDescription(long propertyRepairId, String longDescription) {
        return propertyRepairRepository.updatePropertyRepairByLongDescription(propertyRepairId, longDescription);
    }

    @Override
    public void deletePropertyRepair(long propertyRepairId) {
        propertyRepairRepository.deleteById(propertyRepairId);
    }
}
