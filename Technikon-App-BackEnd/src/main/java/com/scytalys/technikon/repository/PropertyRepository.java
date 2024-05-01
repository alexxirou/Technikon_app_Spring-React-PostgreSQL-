package com.scytalys.technikon.repository;

import com.scytalys.technikon.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {
}
