package com.scytalys.technikon.repository;

import com.scytalys.technikon.domain.PropertyOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PropertyOwnerRepository extends JpaRepository<PropertyOwner, UUID> {
}
