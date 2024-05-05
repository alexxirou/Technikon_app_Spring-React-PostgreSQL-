package com.scytalys.technikon.repository;
import com.scytalys.technikon.domain.PropertyOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyOwnerRepository extends JpaRepository<PropertyOwner, Long> {


    Optional<PropertyOwner> findByUsername(String username);


    Optional<PropertyOwner> findByEmail(String email);
}
