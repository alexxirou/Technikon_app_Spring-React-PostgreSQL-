package com.scytalys.technikon.repository;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.service.specifications.UserSearchSpecification;
import org.springframework.data.jpa.domain.Specification;
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

//
//    @Modifying
//    @Transactional
//    @Query("UPDATE PropertyOwner p set p.isActive = false, p.version = p.version + 1  where p.tin=:tin and p.version= :version")
//    int softDeleteByTin(@Param("tin")String tin, @Param("version")long version);

//    @Modifying
//    @Transactional
//    @Query("UPDATE PropertyOwner p " +
//            "SET p.email = CASE WHEN :email IS NOT NULL AND p.email <> :email THEN :email ELSE p.email END, " +
//            "    p.password = CASE WHEN :password IS NOT NULL AND p.password <> :password THEN :password ELSE p.password END, " +
//            "    p.address = CASE WHEN :address IS NOT NULL AND p.address <> :address THEN :address ELSE p.address END, " +
//            "    p.version = p.version + 1 " +
//            "WHERE p.tin = :tin AND p.version = :version")
//    int update(@Param("tin") String tin,
//               @Param("email") String email,
//               @Param("password") String password,
//               @Param("address") String address,
//               @Param("version") long version);



    @Transactional
    @Query("SELECT p.id FROM Property p WHERE p.propertyOwner.id = :id")
    List<String> findPropertyIdsByUserId(@Param("id") long id);

//    @Transactional
//    @Modifying
//    @Query("DELETE FROM PropertyOwner p WHERE p.tin = :tin")
//    int deleteByTin(@Param("tin") String tin);



    Optional<PropertyOwner> findByTin(String tin);
    Optional<PropertyOwner> findByUsername(String username);
    Optional<List<PropertyOwner>> findAll(Specification<User> spec);


    Optional<PropertyOwner> findByName(String username);

//    @Query("SELECT new com.scytalys.technikon.dto.user.UserSearchResponseDto(p.tin, p.username, p.email, p.name, p.surname, p.address, p.phoneNumber, p.isActive, FUNCTION('ARRAY_AGG', prop.tin), p.version) " +
//            "FROM PropertyOwner p " +
//            "LEFT JOIN Property prop ON p.id = prop.propertyOwner.id " +
//            "WHERE (:username IS NOT NULL AND p.username = :username) " +
//            "   OR (:email IS NOT NULL AND p.email = :email) " +
//            "   OR (:tin IS NOT NULL AND p.tin = :tin) " +
//            "GROUP BY p.tin, p.username, p.email, p.name, p.surname, p.address, p.phoneNumber, p.isActive, p.version")
//    Optional<UserSearchResponseDto> searchUserAndFindPropertyIds(@Param("tin") String tin, @Param("username") String username, @Param("email") String email);

}
