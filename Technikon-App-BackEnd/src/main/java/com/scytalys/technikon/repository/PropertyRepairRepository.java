package com.scytalys.technikon.repository;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.RepairType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface PropertyRepairRepository extends JpaRepository<PropertyRepair, Long> {

    @Query("SELECT p FROM PropertyRepair p WHERE p.dateOfRepair= :date")
    List<PropertyRepair> getPropertyRepairByDate(@Param("date") LocalDate date);

    @Query("SELECT p FROM PropertyRepair p WHERE p.dateOfRepair>= :firstDate AND p.dateOfRepair<= :lastDate")
    List<PropertyRepair> getPropertyRepairByDates(@Param("firstDate") LocalDate firstDate,@Param("lastDate") LocalDate lastDate);

    @Query("SELECT p FROM PropertyRepair p WHERE p.propertyOwner.id= :id")
    List<PropertyRepair> getPropertyRepairs(@Param("id") long propertyOwnerId);

    @Query("UPDATE PropertyRepair p SET p.dateOfRepair= :date WHERE p.id= :id")
    boolean updatePropertyRepairByDate(@Param("id")long propertyRepairId, @Param("date") LocalDate date);

    @Query("UPDATE PropertyRepair p SET p.shortDescription= :description WHERE p.id= :id")
    boolean updatePropertyRepairByShortDescription(@Param("id") long propertyRepairId, @Param("description") String shortDescription);

    @Query("UPDATE PropertyRepair p SET p.repairType= :type WHERE p.id= :id")
    boolean updatePropertyRepairByRepairType(@Param("id") long propertyRepairId, @Param("type")RepairType repairType);

    @Query("UPDATE PropertyRepair p SET p.cost= :cost WHERE p.id= :id")
    boolean updatePropertyRepairByCost(@Param("id") long propertyRepairId,@Param("cost") double cost);

    @Query("UPDATE PropertyRepair p SET p.longDescription= :description WHERE p.id= :id")
    boolean updatePropertyRepairByLongDescription(@Param("id") long propertyRepairId,@Param("description") String longDescription);
}
