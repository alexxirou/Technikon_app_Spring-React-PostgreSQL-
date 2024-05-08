package com.scytalys.technikon.service.impl;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;

import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.service.PropertyOwnerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@AllArgsConstructor
public class PropertyOwnerServiceImpl implements PropertyOwnerService {

    private final PropertyOwnerRepository propertyOwnerRepository;

    /**
     * Creates a new user in the repository as a PropertyOwner type
     *
     * @param user The user to be created.
     * @return The created user.
     */
    @Override
    @Transactional
    public User createDBUser(User user) {
        return propertyOwnerRepository.save(user);
    }

    /**
     * Searches for a user in the repository by their ID.
     *
     * @param id The ID of the user to be searched.
     * @return The found user or null
     *
     */
    @Override
    public User searchUserById(long id) {
        return propertyOwnerRepository.findById(id).filter(User::isActive)
                .orElse(null);
    }

    /**
     * Searches for a user in the repository by their username.
     *
     * @param username The username of the user to be searched.
     * @return The found user or null.
     *
     */
    @Override
    public User searchUserByUsername(String username) {
        return propertyOwnerRepository.findByUsername(username).filter(User::isActive)
                .orElse(null);
    }

    /**
     * Searches for a user in the repository by their username.
     *
     * @param email The email of the user to be searched.
     * @return The found user or null
     *
     */
    @Override
    public User searchUserByEmail(String email) {
        return propertyOwnerRepository.findByEmail(email).filter(User::isActive)
                .orElse(null);
    }

    /**
     * Verify if the user is null.
     *
     * @param propertyOwner the user.
     * @throws EntityNotFoundException if user is null
     */

    public void verifySearchResult(User propertyOwner){
        if (propertyOwner==null) throw new EntityNotFoundException("User not found.");
    }

    /**
     * Updates the email of a user in the repository.
     *
     * @param email the email column
     * @param id the id of row to update.
     * @param version checks the record is updated correctly.
     *
     */

    public int updateUserEmail(long id, String email, long version) {
        email=email.toLowerCase();
        return propertyOwnerRepository.updateEmail(id, email, version);

    }

    /**
     * Updates the address of a user in the repository.
     *
     * @param address the address column
     * @param id the id of row to update.
     * @param version   checks the record is updated correctly.
     */
    @Transactional
    public int updateUserAddress(long id, String address, long version) {
        return propertyOwnerRepository.updateAddress(id, address, version);
    }


    /**
     * Updates the password of a user in the repository.
     *
     * @param password the password column
     * @param id of the row to update
     * @param version   checks the record is updated correctly.
     */

    public int updateUserPassword(long id, String password, long version) {
        return propertyOwnerRepository.updatePassword(id, password, version);
    }

    /**
     * Deletes a user from the repository by their ID.
     *
     * @param propertyOwnerId The ID of the user to be deleted.
     */


    public void deleteUser(long propertyOwnerId) {
        propertyOwnerRepository.deleteById(propertyOwnerId);
    }

    /**
     * Performs a soft delete on a user in the repository
     *
     * @param id of the row to be deactivated
     * @param version the version of the row to be soft deleted
     */

    public int softDeleteUser(long id, long version){

      return propertyOwnerRepository.softDeleteByid(id,version);
    }

    /**
     * Creates a UserResponseDto object for a user  operation.
     *
     * @param id The ID of the user to be searched.
     * @param username The username of the user to be searched.
     * @param email The email of the user to be searched.
     *
     * @return The created UserResponseToSearchDto object.
     */
    public UserResponseDto createUserResponseDto(long id, String username, String email , long version){
        return new UserResponseDto(
                id,
                username,
                email,
                version);
    }


    /**
     * Verifies that the object does not have duplicate fields that should be unique in the db
     *
     * @param id the id column
     * @throws DataIntegrityViolationException If an unique field matches the db
     */
    public void verifyConstraintsId(Long id){
        PropertyOwner test= propertyOwnerRepository.findById(id).orElse(null);
        if (test!=null) throw new DataIntegrityViolationException("User id already exists.");

    }

    /**
     * Verifies that the object does not have duplicate fields that should be unique in the db
     *
     * @param username the username column
     * @throws DataIntegrityViolationException If an unique field matches the db
     */
    public void verifyConstraintsUsername(String username){
        PropertyOwner test= propertyOwnerRepository.findByUsername(username).orElse(null);
        if (test!=null) throw new DataIntegrityViolationException("Username already exists.");

    }

    /**
     * Verifies that the object does not have duplicate fields that should be unique in the db
     *
     * @param email the email column
     * @throws DataIntegrityViolationException If an unique field matches the db
     */
    public void verifyConstraintsEmail(String email){
        PropertyOwner test= propertyOwnerRepository.findByEmail(email).orElse(null);
        if (test!=null) throw new DataIntegrityViolationException("Email already exists.");

    }

    /**
     *Returns the ids of properties linked to a  User
     * @param userId the id of the user
     * @return a list of the property ids linked to a User
    */
    public ArrayList<Long> findPropertiesForUser(long userId){
        return propertyOwnerRepository.findPropertyIdsByUserId(userId);

    }



    /**
     * Verify if a user is linked to a property
     * @param results the list of property ids linked to a user.
     * @return a boolean based on if the propertyId list is empty or not
     */
    public boolean checkUserHasProperties(List<Long> results){

        return !results.isEmpty(); // Return true if the list of property IDs is not empty
    }
}



