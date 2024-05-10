package com.scytalys.technikon.service.impl;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.dto.UserUpdateDto;
import com.scytalys.technikon.mapper.OwnerMapper;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.service.PropertyOwnerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PropertyOwnerServiceImpl implements PropertyOwnerService {

    private final PropertyOwnerRepository propertyOwnerRepository;
    @Autowired
    private final OwnerMapper OwnerMapper;

    /**
     * Creates a new user in the repository as a PropertyOwner type
     *
     * @param user The user to be created.
     * @return ResponseDto.
     * throws IllegalArgumentException if important fields are null;
     * throws DataIntegrityViolationException if unique field constraint violation
     */
    @Override
    public PropertyOwner createDBUser(PropertyOwner user) {
        try {
            propertyOwnerRepository.save(user);
            return user;
        }catch (IllegalArgumentException e) {
            throw e;
        }catch (Exception e){
            throw new DataIntegrityViolationException("Id, Username, or Email already taken.");
        }
    }


    /**
     * Searches for a user in the repository by their username.
     *
     * @param user The user object containing search parameters.
     * @return The found user or null if not found or inactive.
     */
    @Override
    public User searchUser(User user) {
        return propertyOwnerRepository.search(user.getUsername(), user.getEmail(), user.getId())
                .filter(User::isActive)
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
        int res =propertyOwnerRepository.update(dto.id(), dto.email(), dto.address(), dto.password(), dto.version());
        if (res==0) throw new DataAccessResourceFailureException("Update failed: User not found, or resource is busy.");
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
      int res = propertyOwnerRepository.softDeleteById(id,version);
      if (res==0) throw new DataAccessResourceFailureException("User not found, or resource is busy.0");
      return res;
    }

    /**
     * Creates a UserResponseDto object for a user  operation.
     *
     * @param user the User object
     * @return The created UserResponseDto object.
     */
    public UserResponseDto createUserResponseDto(User user){
        return OwnerMapper.userToUserResponseDto(user);
    }




    /**
     *Returns the ids of properties linked to a  User
     * @param userId the id of the user
     * @return a boolean based on if the user is linked to any property ids
    */
    public boolean checkUserHasProperties(long userId){
        List<Long> results=propertyOwnerRepository.findPropertyIdsByUserId(userId);
        return !results.isEmpty(); // Return true if the list of property IDs is not empty
    }


}



