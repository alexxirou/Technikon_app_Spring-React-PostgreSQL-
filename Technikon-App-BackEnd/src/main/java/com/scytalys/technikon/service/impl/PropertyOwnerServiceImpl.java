package com.scytalys.technikon.service.impl;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;


import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.dto.UserUpdateDto;
import com.scytalys.technikon.mapper.UserMapper;
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



@Service
@AllArgsConstructor
public class PropertyOwnerServiceImpl implements PropertyOwnerService {

    private final PropertyOwnerRepository propertyOwnerRepository;
    @Autowired
    private final UserMapper userMapper;

    /**
     * Creates a new user in the repository as a PropertyOwner type
     *
     * @param user The user to be created.
     * @return ResponseDto.
     * throws IllegalArgumentException if important fields are null;
     * throws DataIntegrityViolationException if unique field constraint violation
     */
    @Override
    public UserResponseDto createDBUser(PropertyOwner user) {
        try {
            propertyOwnerRepository.save(user);
            return createUserResponseDto(user);
        }catch (IllegalArgumentException e) {
            throw e;
        }catch (Exception e){
            throw new DataIntegrityViolationException("Id, Username, or Email already taken.");
        }
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
     * Updates a User in the DB
     * @param dto the Update Information record.
     * @Throws EntityNotFoundException, IllegalArgumentException.
     */
    public void UpdateUser(UserUpdateDto dto){
        User user =searchUserById(dto.id());
        if (user==null) throw new EntityNotFoundException("User not found.");
        propertyOwnerRepository.save(userMapper.userUpdateDtoToOwner(dto,user));
    }

    /**
     * Deletes a user from the repository by their ID.
     *
     * @param propertyOwnerId The ID of the user to be deleted.
     */

    public void deleteUser(long propertyOwnerId) {
        try {
            propertyOwnerRepository.deleteById(propertyOwnerId);
        }catch (Exception e){
            throw new EntityNotFoundException("User not found.");
        }
    }

    /**
     * Performs a soft delete on a user in the repository
     *
     * @param id of the row to be deactivated
     * @param version the version of the row to be soft deleted
     */

    public int softDeleteUser(long id, long version){
      int res = propertyOwnerRepository.softDeleteByid(id,version);
      if (res==0) throw new DataAccessResourceFailureException("User not found, or resource is busy.0");
      return res;
    }

    /**
     * Creates a UserResponseDto object for a user  operation.
     *
     * @param user the User object
     * @return The created UserResponseToSearchDto object.
     */
    public UserResponseDto createUserResponseDto(User user){
        return userMapper.userToUserResponseDto(user);
    }




    /**
     *Returns the ids of properties linked to a  User
     * @param userId the id of the user
     *  @return a boolean based on if the user is linked to any property ids
    */
    public boolean checkUserHasProperties(long userId){
        List<Long> results=propertyOwnerRepository.findPropertyIdsByUserId(userId);
        return !results.isEmpty(); // Return true if the list of property IDs is not empty
    }




}



