package com.scytalys.technikon.repository;

import com.scytalys.technikon.domain.PropertyOwner;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyOwnerRepository extends JpaRepository<PropertyOwner, Long> {

    @Transactional
    Optional<PropertyOwner> findByUsername(String username);


    Optional<PropertyOwner> findByEmail(String email);
}
