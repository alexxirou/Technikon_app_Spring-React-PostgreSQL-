package com.scytalys.technikon.repository;

import com.scytalys.technikon.domain.PropertyRepair;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
@Repository
public class RepoPropertyRepair {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void update(PropertyRepair newPropertyRepair, long propertyRepairId) throws IllegalAccessException {
        PropertyRepair existingPropertyRepair = entityManager.find(PropertyRepair.class, propertyRepairId);

        Class<?> propertyRepairClass = PropertyRepair.class;
        Field[] propertyRepairFields = propertyRepairClass.getDeclaredFields();

        for(Field field : propertyRepairFields){
            field.setAccessible(true);
            Object value= field.get(newPropertyRepair);
            if(value!=null){
                field.set(existingPropertyRepair, value);
            }
            field.setAccessible(false);
        }
        entityManager.merge(existingPropertyRepair);
    }
}
