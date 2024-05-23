package com.scytalys.technikon.repository;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyRepair;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    Property findPropertyByTin(@Param("tin") String tin);

    @Query("SELECT p FROM Property p WHERE p.latitude= :latitude AND p.longitude= :longitude")
    List <Property> findByArea(@Param("latitude") double latitude, @Param("longitude") double longitude);

}