package com.scytalys.technikon.repository;

import com.scytalys.technikon.domain.Property;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Transactional
    @Modifying
    @Query("select  p from Product p where p.quantity = :quantity")
    List<Property> findAllPropertyName(@Param("address") String address);
}
