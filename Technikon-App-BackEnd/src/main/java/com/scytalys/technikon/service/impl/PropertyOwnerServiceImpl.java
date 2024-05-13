package com.scytalys.technikon.service.impl;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.*;
import com.scytalys.technikon.mapper.OwnerMapper;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.service.PropertyOwnerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class PropertyOwnerServiceImpl implements PropertyOwnerService {

    private final PropertyOwnerRepository propertyOwnerRepository;
    @Autowired
    private final OwnerMapper ownerMapper;

    /**
     * Creates a new user in the repository as a PropertyOwner type
     *
     * @param dto The dto containing information of the user to be created.
     * @return ResponseDto.
     * throws IllegalArgumentException if important fields are null;
     * throws DataIntegrityViolationException if unique field constraint violation
     */
    @Override
    public PropertyOwner createDBUser(UserCreationDto dto) {
        PropertyOwner user = ownerMapper.userCreationDtoToPropertyOwner(dto);
        user.setEmail(user.getEmail().toLowerCase());
        try {
            propertyOwnerRepository.save(user);
            return user;
        }catch (Exception e){
            throw new DataIntegrityViolationException("Id, Username, or Email already taken.");
        }
    }


    /**
     * Searches for a user in the repository by their username.
     *
     * @param dto The  dto containing search parameters.
     * @return The found user or null if not found or inactive.
     */
    @Override
    public UserSearchResponseDto searchUser(UserSearchDto dto) {
        return propertyOwnerRepository.searchUserAndFindPropertyIds(dto.tin(),dto.username(), dto.Email())
                .orElse(null);
    }



    /**
     * Updates a user in the database.
     *
     * @param dto The DTO containing update information.
     * @throws EntityNotFoundException if the user to be updated is not found.
     * @throws IllegalArgumentException if the provided DTO is invalid or contains incomplete information.
     */
    public void UpdateUser(UserUpdateDto dto){
        String verifiedEmail = Optional.ofNullable(dto.email())
                .filter(email -> Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email))
                .orElseThrow(() -> new IllegalArgumentException("Invalid email format"));
        int res =propertyOwnerRepository.update(dto.tin(), verifiedEmail, dto.address(), dto.password(), dto.version());
        if (res==0) throw new DataAccessResourceFailureException("Update failed: User not found, or resource is busy.");
    }

    /**
     * Deletes a user from the repository by their ID.
     *
     * @param tin The tin of the user to be deleted.
     */

    public void deleteUser(String tin) {
        int res = propertyOwnerRepository.deleteByTin(tin);

        if (res==0)  throw new EntityNotFoundException("User not found.");

    }

    /**
     * Performs a soft delete on a user in the repository if the version matches
     *
     * @param tin of the row to be deactivated
     * @param version the version sent by the controller
     */

    public int softDeleteUser(String tin, long version){
      int res = propertyOwnerRepository.softDeleteByTin(tin,version);
      if (res==0) throw new DataAccessResourceFailureException("User not found, or resource is busy. " +
              "If the error persists please contact an administrator.");
      return res;
    }

    /**
     * Creates a UserResponseDto object for a user  operation.
     *
     * @param user the User object
     * @return The created UserResponseDto object.
     */
    public UserResponseDto createUserResponseDto(User user){
        return ownerMapper.userToUserResponseDto(user);
    }




    /**
     *Returns the ids of properties linked to a  User
     * @param tin The tin of the user
     * @return a boolean based on if the user is linked to any property ids
    */
    public boolean checkUserHasProperties(String tin){
        List<String> results=propertyOwnerRepository.findPropertyIdsByUserId(tin);
        return !results.isEmpty(); // Return true if the list of property IDs is not empty
    }

    /**
     * Creates a Dto containing the user info and the properties associated with him.
     * @param user the user
     * @return a userSearchResponse record containing the information.
     */
    @Override
    public UserSearchResponseDto createSearchResponse(User user){
        List<String> results=propertyOwnerRepository.findPropertyIdsByUserId(user.getTin());

        return ownerMapper.userToUserSearchResponseDto(user, results);
    }

}



