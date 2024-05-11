package com.scytalys.technikon.repository;
import com.scytalys.technikon.domain.PropertyOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public interface PropertyOwnerRepository extends JpaRepository<PropertyOwner, Long> {


    @Modifying
    @Transactional
    @Query("UPDATE PropertyOwner p set p.isActive = false, p.version = p.version + 1  where p.tin=:tin and p.version= :version")
    int softDeleteByTin(@Param("tin")String tin, @Param("version")long version);

    @Modifying
    @Transactional
    @Query("UPDATE PropertyOwner p " +
            "SET p.email = CASE WHEN :email IS NOT NULL AND p.email <> :email THEN LOWER(:email) ELSE p.email END, " +
            "    p.password = CASE WHEN :password IS NOT NULL AND p.password <> :password THEN :password ELSE p.password END, " +
            "    p.address = CASE WHEN :address IS NOT NULL AND p.address <> :address THEN :address ELSE p.address END, " +
            "    p.version = p.version + 1 " +
            "WHERE p.tin = :tin AND p.version = :version")
    int update(@Param("tin") String tin,
               @Param("email") String email,
               @Param("password") String password,
               @Param("address") String address,
               @Param("version") long version);


    @Query("SELECT p FROM PropertyOwner p " +
            "WHERE (:username IS NOT NULL AND p.username = :username) " +
            "  OR (:email IS NOT NULL AND p.email = :email) " +
            "  OR (:tin IS NOT NULL AND p.tin = :tin)")
    Optional<PropertyOwner> search(@Param("username") String username,
                    @Param("email") String email,
                    @Param("tin") String tin);

    @Transactional
    @Query("SELECT p.tin FROM Property p WHERE p.propertyOwner.tin = :tin")
    List<String> findPropertyIdsByUserId(@Param("tin") String tin);

    @Transactional
    @Modifying
    @Query("DELETE FROM PropertyOwner p WHERE p.tin = :tin")
    int deleteByTin(@Param("tin") String tin);
}
