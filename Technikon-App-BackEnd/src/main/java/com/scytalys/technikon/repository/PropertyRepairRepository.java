package com.scytalys.technikon.repository;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface PropertyRepairRepository extends JpaRepository<PropertyRepair, Long> {

    @Query("SELECT p FROM PropertyRepair p WHERE p.propertyOwner.id= :propertyOwnerId AND p.property.id= :propertyId AND p.id= :repairId")
    PropertyRepair getPropertyRepair(@Param("propertyOwnerId") long propertyOwnerId, @Param("propertyId") long propertyId, @Param("repairId") long repairId);

    @Query("SELECT p FROM PropertyRepair p WHERE p.propertyOwner.id= :propertyOwnerId AND p.property.id= :propertyId")
    List<PropertyRepair> getPropertyRepairs(@Param("propertyOwnerId") long propertyOwnerId, @Param("propertyId") long propertyId);

    @Query("SELECT p FROM PropertyRepair p WHERE p.propertyOwner.id= :propertyOwnerId AND p.property.id= :propertyId AND p.dateOfRepair= :date")
    List<PropertyRepair> getPropertyRepairByDate(@Param("propertyOwnerId") long propertyOwnerId, @Param("propertyId") long propertyId, @Param("date") LocalDate date);

    @Query("SELECT p FROM PropertyRepair p WHERE p.propertyOwner.id= :propertyOwnerId AND p.property.id= :propertyId AND p.dateOfRepair>= :firstDate AND p.dateOfRepair<= :lastDate")
    List<PropertyRepair> getPropertyRepairByDates(@Param("propertyOwnerId") long propertyOwnerId, @Param("propertyId") long propertyId, @Param("firstDate") LocalDate firstDate, @Param("lastDate") LocalDate lastDate);

    @Transactional
    @Modifying
    @Query("UPDATE PropertyRepair p SET p.dateOfRepair= :newDate WHERE p.propertyOwner.id= :propertyOwnerId AND p.property.id= :propertyId AND p.id= :id")
    int updatePropertyRepairByDate(@Param("propertyOwnerId") long propertyOwnerId, @Param("propertyId") long propertyId, @Param("id") long id, @Param("newDate") LocalDate newDate);

    @Transactional
    @Modifying
    @Query("UPDATE PropertyRepair p SET p.shortDescription= :newDesc WHERE p.propertyOwner.id= :propertyOwnerId AND p.property.id= :propertyId AND p.id= :id")
    int updatePropertyRepairByShortDescription(@Param("propertyOwnerId") long propertyOwnerId, @Param("propertyId") long propertyId, @Param("id") long id, @Param("newDesc") String newDesc);

    @Transactional
    @Modifying
    @Query("UPDATE PropertyRepair p SET p.repairType= :newType WHERE p.propertyOwner.id= :propertyOwnerId AND p.property.id= :propertyId AND p.id= :id")
    int updatePropertyRepairByType(@Param("propertyOwnerId") long propertyOwnerId, @Param("propertyId") long propertyId, @Param("id") long id, @Param("newType") RepairType newType);


    @Transactional
    @Modifying
    @Query("UPDATE PropertyRepair p SET p.cost= :newCost WHERE p.propertyOwner.id= :propertyOwnerId AND p.property.id= :propertyId AND p.id= :id")
    int updatePropertyRepairByCost(@Param("propertyOwnerId") long propertyOwnerId, @Param("propertyId") long propertyId, @Param("id") long id, @Param("newCost") BigDecimal newCost);


    @Transactional
    @Modifying
    @Query("UPDATE PropertyRepair p SET p.longDescription= :newLongDesc WHERE p.propertyOwner.id= :propertyOwnerId AND p.property.id= :propertyId AND p.id= :id")
    int updatePropertyRepairByLongDescription(@Param("propertyOwnerId") long propertyOwnerId, @Param("propertyId") long propertyId, @Param("id") long id, @Param("newLongDesc") String newLongDesc);

    @Transactional
    @Modifying
    @Query("DELETE PropertyRepair p WHERE p.propertyOwner.id= :propertyOwnerId AND p.property.id= :propertyId AND p.id= :id")
    int deletePropertyRepair(@Param("propertyOwnerId") long propertyOwnerId, @Param("propertyId") long propertyId, @Param("id") long id);
}
