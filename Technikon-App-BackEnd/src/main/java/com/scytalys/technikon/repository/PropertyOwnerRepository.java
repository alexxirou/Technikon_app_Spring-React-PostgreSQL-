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
    @Query("UPDATE PropertyOwner p SET p.email = :email WHERE p.id = :id")
    void updatePropertyOwnerEmailById(@Param("id") long id, @Param("email") String email);

    @Transactional
    @Modifying
    @Query("UPDATE PropertyOwner p SET p.address = :address WHERE p.id = :id")
    void updatePropertyOwnerAddressById(@Param("id") long id, @Param("address") String address);

    @Transactional
    @Modifying
    @Query("UPDATE PropertyOwner p SET p.password = :password WHERE p.id = :id")
    void updatePropertyOwnerPasswordById(@Param("id") long id, @Param("password") String password);

    @Transactional
    @Modifying
    @Query("UPDATE PropertyOwner p SET p.isActive = false WHERE p.id = :id")
    void softDeleteById(@Param("id") long id);

}
