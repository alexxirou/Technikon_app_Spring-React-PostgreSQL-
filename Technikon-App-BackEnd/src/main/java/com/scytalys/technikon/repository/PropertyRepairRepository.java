package com.scytalys.technikon.repository;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
public interface PropertyRepairRepository extends JpaRepository<PropertyRepair, Long>, JpaSpecificationExecutor<PropertyRepair> {

    @Query("SELECT p FROM PropertyRepair p WHERE p.propertyOwner.id= :propertyOwnerId")
    List<PropertyRepair> getPropertyRepairsByOwner(@Param("propertyOwnerId") long propertyOwnerId);

    @Query("SELECT p FROM PropertyRepair p WHERE p.propertyOwner.id= :propertyOwnerId AND p.dateOfRepair= :date")
    List<PropertyRepair> getPropertyRepairByDate(@Param("propertyOwnerId") long propertyOwnerId, @Param("date") LocalDate date);

    @Query("SELECT p FROM PropertyRepair p WHERE p.propertyOwner.id= :propertyOwnerId AND p.dateOfRepair>= :firstDate AND p.dateOfRepair<= :lastDate")
    List<PropertyRepair> getPropertyRepairByDates(@Param("propertyOwnerId") long propertyOwnerId, @Param("firstDate") LocalDate firstDate, @Param("lastDate") LocalDate lastDate);
}
