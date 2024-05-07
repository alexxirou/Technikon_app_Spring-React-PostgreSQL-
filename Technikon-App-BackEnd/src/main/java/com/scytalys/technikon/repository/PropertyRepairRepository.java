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
import java.util.UUID;
@Repository
public interface PropertyRepairRepository extends JpaRepository<PropertyRepair, Long> {
    @Query("SELECT p FROM PropertyRepair p WHERE p.propertyOwner.id= :id")
    List<PropertyRepair> searchPropertyRepairs(@Param("id") long propertyOwnerId);

//    @Modifying
//    @Transactional
//    @Query("UPDATE PropertyRepair p SET " +
//            "p.dateOfRepair = CASE WHEN cast(:newDateOfRepair as timestamp)IS NULL THEN p.dateOfRepair ELSE cast(:newDateOfRepair as timestamp) END, " +
//            "p.cost = CASE WHEN :newCost IS NULL THEN p.cost ELSE :newCost END, " +
//            "p.shortDescription = CASE WHEN :newShortDescription IS NULL THEN p.shortDescription ELSE :newShortDescription END, " +
//            "p.longDescription = CASE WHEN :newLongDescription IS NULL THEN p.longDescription ELSE :newLongDescription END, " +
//            "p.repairType = CASE WHEN :newRepairType IS NULL THEN p.repairType ELSE :newRepairType END, " +
//            "p.repairStatus = CASE WHEN :newRepairStatus IS NULL THEN p.repairStatus ELSE :newRepairStatus END "+
//            "WHERE p.id = :propertyRepairId")
//    int updatePropertyRepair(@Param("newDateOfRepair") Date newDateOfRepair,
//                             @Param("newCost") Double newCost,
//                             @Param("newShortDescription") String newShortDescription,
//                             @Param("newLongDescription") String newLongDescription,
//                             @Param("newRepairType") String newRepairType,
//                             @Param("newRepairStatus") String newRepairStatus,
//                             @Param("propertyRepairId") long propertyRepairId);

    @Query("SELECT p FROM PropertyRepair p WHERE p.propertyOwner.id= :id AND p.dateOfRepair= :date")
    List<PropertyRepair> searchPropertyRepairByDate(@Param("id") long propertyOwnerId, @Param("date") LocalDate date);

    @Query("SELECT p FROM PropertyRepair p WHERE p.propertyOwner.id= :id AND p.dateOfRepair>= :firstDate AND p.dateOfRepair<= :lastDate")
    List<PropertyRepair> searchPropertyRepairByDates(@Param("id") long propertyOwnerId, @Param("firstDate") LocalDate firstDate,@Param("lastDate") LocalDate lastDate);

    @Transactional
    @Modifying
    @Query("DELETE PropertyRepair p WHERE p.propertyOwner.id= :propertyOwnerId AND p.id= :id")
    int deletePropertyRepair(@Param("propertyOwnerId") long propertyOwnerId, @Param("id") long propertyRepairId);

}
