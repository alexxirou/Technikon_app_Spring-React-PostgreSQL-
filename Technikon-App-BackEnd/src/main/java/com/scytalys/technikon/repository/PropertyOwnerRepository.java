package com.scytalys.technikon.repository;
import com.scytalys.technikon.domain.PropertyOwner;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PropertyOwnerRepository extends JpaRepository<PropertyOwner, Long> {


    @Modifying
    @Transactional
    @Query("UPDATE PropertyOwner p set p.isActive = false where p.id=:id and p.version=version")
    Optional<PropertyOwner> softDeleteByid(long id, long version);

    @Modifying
    @Transactional
    @Query("UPDATE PropertyOwner p set p.email= :email where p.id=:id and p.version=version")
    Optional<PropertyOwner> updateEmail(long id, String email, long version);

    @Modifying
    @Transactional
    @Query("UPDATE PropertyOwner p set p.address= :address where p.id=:id and p.version=version")
    Optional<PropertyOwner> updateAddress(long id, String address, long version);

    @Modifying
    @Transactional
    @Query("UPDATE PropertyOwner p set p.password= :password where p.id=:id and p.version=version")
    Optional<PropertyOwner> updatePassword(long id, String password, long version);

    Optional<PropertyOwner> findByUsername(String username);


    Optional<PropertyOwner> findByEmail(String email);
}
