package com.scytalys.technikon.repository;
import com.scytalys.technikon.domain.PropertyOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyOwnerRepository extends JpaRepository<PropertyOwner, Long> {


    @Modifying
    @Transactional
    @Query("UPDATE PropertyOwner p set p.isActive = false where p.id=:id and p.version= :version")
    int softDeleteByid(@Param("id")long id, @Param("version")long version);

    @Modifying
    @Transactional
    @Query("UPDATE PropertyOwner p set p.email= :email where p.id=:id and p.version= :version")
    int updateEmail(@Param("id")long id, @Param("email")String email, @Param("version")long version);

    @Modifying
    @Transactional
    @Query("UPDATE PropertyOwner p SET p.address = :address WHERE p.id = :id AND p.version = :version")
    int updateAddress(@Param("id") long id, @Param("address") String address, @Param("version") long version);


    @Modifying
    @Transactional
    @Query("UPDATE PropertyOwner p set p.password= :password where p.id=:id and p.version= :version")
    int updatePassword(@Param("id")long id, @Param("password")String password, @Param("version")long version);

    Optional<PropertyOwner> findByUsername(String username);


    Optional<PropertyOwner> findByEmail(String email);

    @Transactional
    @Query("SELECT p.id FROM Property p WHERE p.propertyOwner.id = :userId")
    List<Long> findPropertyIdsByUserId(@Param("userId") long userId);


}
