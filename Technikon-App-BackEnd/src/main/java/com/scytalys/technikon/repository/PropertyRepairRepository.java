package com.scytalys.technikon.repository;

import com.scytalys.technikon.domain.PropertyRepair;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;



import java.time.LocalDate;

import java.util.List;

@Repository
public interface PropertyRepairRepository extends JpaRepository<PropertyRepair, Long>, JpaSpecificationExecutor<PropertyRepair> {

    @Query("SELECT p FROM PropertyRepair p WHERE p.propertyOwner.id= :propertyOwnerId")
    List<PropertyRepair> getPropertyRepairsByOwner(@Param("propertyOwnerId") long propertyOwnerId);

    @Query("SELECT p FROM PropertyRepair p WHERE p.propertyOwner.id= :propertyOwnerId AND p.dateOfRepair= :date")
    List<PropertyRepair> getPropertyRepairByDate(@Param("propertyOwnerId") long propertyOwnerId, @Param("date") LocalDate date);

    @Query("SELECT p FROM PropertyRepair p WHERE p.propertyOwner.id= :propertyOwnerId AND p.dateOfRepair>= :firstDate AND p.dateOfRepair<= :lastDate")
    List<PropertyRepair> getPropertyRepairsByDates(@Param("propertyOwnerId") long propertyOwnerId, @Param("firstDate") LocalDate firstDate, @Param("lastDate") LocalDate lastDate);

    @Query("SELECT p FROM PropertyRepair p WHERE p.property.id= :id")
    List<PropertyRepair> getPropertyRepairsByProperty(@Param("id") long propertyId);

