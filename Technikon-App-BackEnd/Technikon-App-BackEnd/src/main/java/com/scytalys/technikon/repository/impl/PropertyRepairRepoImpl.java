package com.scytalys.technikon.repository.impl;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.repository.PropertyRepairRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;

@Repository
public class PropertyRepairRepoImpl implements PropertyRepairRepo {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyRepairRepoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int updatePropertyRepairExistingFields(PropertyRepair newPropertyRepair, long propertyRepairId) {
        try {
            PropertyRepair existingPropertyRepair = entityManager.find(PropertyRepair.class, propertyRepairId);
            if (existingPropertyRepair == null) {
                return 0;
            }
            Class<?> propertyRepairClass = PropertyRepair.class;
            Field[] propertyRepairFields = propertyRepairClass.getDeclaredFields();
            for (Field field : propertyRepairFields) {
                field.setAccessible(true);
                Object value = field.get(newPropertyRepair);
                if (value != null) {
                    field.set(existingPropertyRepair, value);
                }
                field.setAccessible(false);
            }
            entityManager.merge(existingPropertyRepair);        // do we need it ?
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return 1;
    }
}
