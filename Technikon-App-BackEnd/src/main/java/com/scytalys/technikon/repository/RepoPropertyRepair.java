package com.scytalys.technikon.repository;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Repository
public class RepoPropertyRepair {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void update(LocalDate newDateOfRepair,
                       BigDecimal newCost,
                       String newShortDescription,
                       String newLongDescription,
                       RepairType newRepairType,
                       RepairStatus newRepairStatus,
                       long propertyRepairId) {
        PropertyRepair propertyRepair = entityManager.find(PropertyRepair.class, propertyRepairId);
        if (newDateOfRepair != null) {
            propertyRepair.setDateOfRepair(newDateOfRepair);
        }
        if (newCost != null) {
            propertyRepair.setCost(newCost);
        }
        if (newLongDescription != null) {
            propertyRepair.setLongDescription(newLongDescription);
        }
        if (newShortDescription != null) {
            propertyRepair.setShortDescription(newShortDescription);
        }
        if (newRepairStatus != null) {
            propertyRepair.setRepairStatus(newRepairStatus);
        }
        if (newRepairType != null) {
            propertyRepair.setRepairType(newRepairType);
        }
        entityManager.merge(propertyRepair);
    }
}
