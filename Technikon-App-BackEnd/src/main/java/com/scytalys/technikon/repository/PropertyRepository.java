package com.scytalys.technikon.repository;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    Optional<Property> findByTin(String tin);
}
