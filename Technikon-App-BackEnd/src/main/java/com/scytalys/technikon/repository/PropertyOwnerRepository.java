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
    Optional<PropertyOwner> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE PropertyOwner p SET p.email = :email, p.address = :address, p.password = :password WHERE p.id = :id")
    void updatePropertyOwnerById(@Param("id") long id, @Param("email") String email, @Param("address") String address, @Param("password") String password);

    @Transactional
    @Modifying
    @Query("UPDATE PropertyOwner p SET p.isActive = false WHERE p.id = :id")
    void softDeleteById(@Param("id") long id);

}
