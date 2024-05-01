package com.scytalys.technikon.repository;

import com.scytalys.technikon.domain.PropertyRepair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PropertyRepairRepository extends JpaRepository<PropertyRepair, UUID> {
}
