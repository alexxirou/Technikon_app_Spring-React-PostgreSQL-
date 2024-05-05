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
    public PropertyOwner createUser(PropertyOwner user) {
        user.setUsername(user.getName().toLowerCase());
        user.setEmail(user.getEmail().toLowerCase());
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
    public PropertyOwner searchUserById(long id) {
        return propertyOwnerRepository.findById(id).orElse(null);
    }

    /**
     * Searches for a user in the repository by their username.
     *
     * @param username The username of the user to be searched.
     * @return The found user or null.
     *
     */
    @Override
    public PropertyOwner searchUserByUsername(String username) {
        return propertyOwnerRepository.findByUsername(username).orElse(null);
    }

    /**
     * Searches for a user in the repository by their username.
     *
     * @param email The email of the user to be searched.
     * @return The found user or null
     *
     */
    @Override
    public PropertyOwner searchUserByEmail(String email) {
        return propertyOwnerRepository.findByEmail(email).orElse(null);
    }

    /**
     * Verify if the user is null.
     *
     * @param propertyOwner the user.
     * @throws NoSuchElementException if user is null
     */

    public void verifySearchResult(User propertyOwner){
        if (propertyOwner==null) throw new EntityNotFoundException("User not found.");
    }

    /**
     * Updates the email of a user in the repository.
     *
     * @param email the email column
     * @param user the row to update.
     *
     */
    @Transactional
    public void updateUserEmail(String email, User user) {
        user.setEmail(email.toLowerCase());
        propertyOwnerRepository.save((PropertyOwner) user);
    }

    /**
     * Updates the address of a user in the repository.
     *
     * @param address the address column
     * @param propertyOwner the row to update.
     */
    @Transactional
    public void updateUserAddress(String address, PropertyOwner propertyOwner) {
        propertyOwner.setAddress(address);
        propertyOwnerRepository.save(propertyOwner);
    }


    /**
     * Updates the password of a user in the repository.
     *
     * @param password the password column
     * @param user the row to update
     */
    @Transactional
    public void updateUserPassword(String password, User user) {

        user.setPassword(password);
        propertyOwnerRepository.save((PropertyOwner) user);
    }

    /**
     * Deletes a user from the repository by their ID.
     *
     * @param propertyOwnerId The ID of the user to be deleted.
     */

    @Transactional
    public void deleteUser(long propertyOwnerId) {
        propertyOwnerRepository.deleteById(propertyOwnerId);
    }

    /**
     * Performs a soft delete on a user in the repository
     *
     * @param propertyOwner the row to be deactivated
     */
    @Transactional
    public void softDeleteUser(PropertyOwner propertyOwner){
        propertyOwner.setActive(false);
        propertyOwnerRepository.save(propertyOwner);
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
    public UserResponseDto createUserResponseDto(long id, String username, String email){
        return new UserResponseDto(
                id,
                username,
                email);
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
}



