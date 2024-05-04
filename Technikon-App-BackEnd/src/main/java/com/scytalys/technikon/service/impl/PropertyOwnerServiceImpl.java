package com.scytalys.technikon.service.impl;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserUpdateDto;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.dto.UserResponseToSearchDto;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.service.PropertyOwnerService;
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
        return propertyOwnerRepository.save(user);
    }

    /**
     * Searches for a user in the repository by their ID.
     *
     * @param id The ID of the user to be searched.
     * @return The found user.
     * @throws NoSuchElementException If the user is not found.
     */
    @Override
    public PropertyOwner searchUserById(long id) {
        return propertyOwnerRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("User Id not found."));
    }

    /**
     * Searches for a user in the repository by their username.
     *
     * @param username The username of the user to be searched.
     * @return The found user.
     * @throws NoSuchElementException If the user is not found.
     */
    @Override
    public PropertyOwner searchUserByUsername(String username) {
        return propertyOwnerRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("Username not found."));
    }

    /**
     * Searches for a user in the repository by their username.
     *
     * @param username The username of the user to be searched.
     * @return The found user.
     * @throws NoSuchElementException If the user is not found.
     */
    @Override
    public PropertyOwner searchUserByEmail(String email) {
        return propertyOwnerRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementException("Email not found."));
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
        user.setEmail(email);
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
     * Creates a UserResponseDto object for a new user.
     *
     * @param user The user to be created.
     * @param errorMessage The error message, if any.
     * @return The created UserResponseDto object.
     */
    public UserResponseDto createUserCreationResponseDto(User user, String errorMessage){
        return new UserResponseDto((PropertyOwner) user, errorMessage);
    }

    /**
     * Creates a UserResponseToSearchDto object for a user search operation.
     *
     * @param id The ID of the user to be searched.
     * @param username The username of the user to be searched.
     * @param email The email of the user to be searched.
     * @param errorMessage The error message, if any.
     * @return The created UserResponseToSearchDto object.
     */
    public UserResponseToSearchDto createUserSearchResponseDto(long id, String username, String email, String errorMessage){
        return new UserResponseToSearchDto(
                id,
                username,
                email,
                errorMessage);
    }

    /**
     * Creates a UserUpdateDto object for a user update operation.
     *
     * @param id The ID of the user to be updated.
     * @param password The new password of the user.
     * @param email The new email of the user.
     * @param address The new address of the user.
     * @param errorMessage The error message, if any.
     * @return The created UserUpdateDto object.
     */
    public UserUpdateDto createUserUpdateResponseDto(long id, String password, String email, String address, String errorMessage ){
        return new UserUpdateDto(id, address, email, password, errorMessage);
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



