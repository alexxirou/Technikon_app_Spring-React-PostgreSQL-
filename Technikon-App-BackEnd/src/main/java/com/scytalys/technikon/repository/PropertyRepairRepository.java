package com.scytalys.technikon.repository;

import com.scytalys.technikon.domain.PropertyRepair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PropertyRepairRepository extends JpaRepository<PropertyRepair, Long> {
}
