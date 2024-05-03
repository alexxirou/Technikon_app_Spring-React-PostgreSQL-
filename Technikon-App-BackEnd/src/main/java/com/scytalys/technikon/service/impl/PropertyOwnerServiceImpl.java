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
     * Creates a new user in the repository.
     *
     * @param propertyOwner The user to be created.
     * @return The created user.
     */
    @Override
    @Transactional
    public User createUser(PropertyOwner propertyOwner) {
        verifyConstraints(propertyOwner);
        return propertyOwnerRepository.save(propertyOwner);
    }

    /**
     * Searches for a user in the repository by their ID.
     *
     * @param id The ID of the user to be searched.
     * @return The found user.
     * @throws NoSuchElementException If the user is not found.
     */
    @Override
    public PropertyOwner searchUser(long id) {
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
    public PropertyOwner searchUser(String username) {
        return propertyOwnerRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("Username not found."));
    }

    /**
     * Updates the email of a user in the repository.
     *
     * @param propertyOwnerUpdateDto The DTO containing the user's ID and new email.
     */
    @Transactional
    public void updateUserEmail(UserUpdateDto propertyOwnerUpdateDto) {
        PropertyOwner propertyOwner = searchUser(propertyOwnerUpdateDto.id());
        propertyOwner.setEmail(propertyOwnerUpdateDto.email());
        propertyOwnerRepository.save(propertyOwner);
    }

    /**
     * Updates the address of a user in the repository.
     *
     * @param propertyOwnerUpdateDto The DTO containing the user's ID and new address.
     */
    @Transactional
    public void updateUserAddress(UserUpdateDto propertyOwnerUpdateDto) {
        PropertyOwner propertyOwner = searchUser(propertyOwnerUpdateDto.id());
        propertyOwner.setAddress(propertyOwnerUpdateDto.address());
        propertyOwnerRepository.save(propertyOwner);
    }

    /**
     * Updates the password of a user in the repository.
     *
     * @param propertyOwnerUpdateDto The DTO containing the user's ID and new password.
     */
    @Transactional
    public void updateUserPassword(UserUpdateDto propertyOwnerUpdateDto) {
        PropertyOwner propertyOwner = searchUser(propertyOwnerUpdateDto.id());
        propertyOwner.setPassword(propertyOwnerUpdateDto.password());
        propertyOwnerRepository.save(propertyOwner);
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
     * Performs a soft delete on a user in the repository by their ID.
     *
     * @param propertyOwnerId The ID of the user to be soft deleted.
     */
    @Transactional
    public void softDeleteUser(long propertyOwnerId){
        PropertyOwner propertyOwner = searchUser(propertyOwnerId);
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
    public UserResponseDto createUserCreationDto(User user, String errorMessage){
        return new UserResponseDto(user, errorMessage);
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
    public UserResponseToSearchDto createUserSearchDto(long id, String username, String email, String errorMessage){
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
    public UserUpdateDto createUpdateUserDto(long id, String password, String email, String address, String errorMessage ){
        return new UserUpdateDto(id, address, email, password, errorMessage);
    }

    public void verifyConstraints(PropertyOwner propertyOwner){
        PropertyOwner test= propertyOwnerRepository.findById(propertyOwner.getId()).orElse(null);
        if (test!=null) {
            if (test.getId() == propertyOwner.getId()) throw new DataIntegrityViolationException("User id already exists.");
            if (test.getUsername().equalsIgnoreCase(propertyOwner.getUsername())) throw new DataIntegrityViolationException("Username already exists.");

        }

    }
}



