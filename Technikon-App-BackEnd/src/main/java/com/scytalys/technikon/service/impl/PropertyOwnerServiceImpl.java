package com.scytalys.technikon.service.impl;
import com.scytalys.technikon.service.specifications.UserSearchSpecification;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.*;
import com.scytalys.technikon.mapper.OwnerMapper;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.service.PropertyOwnerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class PropertyOwnerServiceImpl implements PropertyOwnerService {

    private final PropertyOwnerRepository propertyOwnerRepository;

    private final OwnerMapper ownerMapper;

    /**
     * Finds a user in the repository of PropertyOwner type
     *
     * @param tin of the user
     * @return the user optional
     * throws EntityNotFoundException if a user is not found.
     *
     */

    @Override
    public PropertyOwner findUser(String tin) {
        return propertyOwnerRepository.findByTin(tin).filter(User::isActive).orElseThrow(() -> new EntityNotFoundException("User not found."));
    }

    /**
     * Searches for a user in the repository by their username.
     *
     * @param dto The  dto containing search parameters.
     * @return The found user.
     * @throws EntityNotFoundException if the user is not found
     */
    @Override
    @Cacheable("PropertyOwners")
    public List<PropertyOwner> searchUser(UserSearchDto dto) {
        Specification<User> spec = Specification.where(null);
        if (dto.tin() != null) {
            spec = spec.and(UserSearchSpecification.tinContains(dto.tin()));
        }
        if(dto.username() != null) {
            spec = spec.and(UserSearchSpecification.usernameContains(dto.username()));
        }
        if (dto.email() != null) {
            spec = spec.and(UserSearchSpecification.emailContains(dto.email()));
        }

        return propertyOwnerRepository.findAll(spec).orElseThrow(()->new EntityNotFoundException("User not found."));

    }




    /**
     * Updates a user in the database.
     *
     * @param dto The DTO containing update information.
     * @throws EntityNotFoundException if the user to be updated is not found.
     * @throws IllegalArgumentException if the provided DTO is invalid or contains incomplete information.
     */
    @Override
    @Transactional
    @CacheEvict(value = "PropertyOwners", allEntries = true)
    public void updateUser(String tin, UserUpdateDto dto){

        PropertyOwner user= findUser(tin);

        PropertyOwner newUser = ownerMapper.updateDtoToUser(dto,user);
        if(dto.email()!=null) {
            String verifiedEmail = Optional.of(dto.email())
                    .filter(email -> Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email format"));
            user.setEmail(verifiedEmail);
        }
        propertyOwnerRepository.save(newUser);

    }



    /**
     * Deletes a user from the repository by their ID.
     *
     * @param tin The tin of the user to be deleted.
     */
    @Override
    @Transactional
    @CacheEvict(value = "PropertyOwners", allEntries = true)
    public void deleteUser(String tin ) {
        PropertyOwner user= findUser(tin);

        propertyOwnerRepository.deleteById(user.getId());;

    }

    /**
     * Performs a soft delete on a user in the repository if the version matches
     *
     * @param tin of the row to be deactivated
     */
    @Override
    @Transactional
    @CacheEvict(value = "PropertyOwners", allEntries = true)
    public void softDeleteUser(String tin){
        PropertyOwner user= propertyOwnerRepository.findByTin(tin).orElseThrow(() -> new EntityNotFoundException("User not found."));

        user.setActive(false);
        propertyOwnerRepository.save(user);

    }

    /**
     * Creates a UserResponseDto object for a user  operation.
     *
     * @param user the User object
     * @return The created UserResponseDto object.
     */
    @Override
    public UserResponseDto createUserResponseDto(PropertyOwner user){
        return ownerMapper.userToUserResponseDto(user);
    }




    /**
     *Returns the ids of properties linked to a  User
     * @param tin The tin of the user
     * @return a boolean based on if the user is linked to any property ids
     */
    @Override

    public boolean checkUserHasProperties(String tin){
        List<String> results=propertyOwnerRepository.findPropertyIdsByUserId(tin);
        return !results.isEmpty(); // Return true if the list of property IDs is not empty
    }

    /**
     * Creates a List of dtos with the relevant user info for each user.
     * @param users the list of users
     * @return a List of  userSearchResponse records containing the information.
     */
    @Override
    public List<UserSearchResponseDto> createSearchResponse(List<PropertyOwner> users){
        return users.stream().map(ownerMapper::userToUserSearchResponseDto).toList();
    }

    /**
     * Method that takes a user object and returns important information from that object and the property tins linked to him.
     * @param user the user to convert to dto
     * @return a response dto containing the userInfo and the property tins associated with him.
     */

    @Override
    public UserDetailsDto userDetails(PropertyOwner user){

        UserSearchResponseDto details = ownerMapper.userToUserSearchResponseDto(user);
        List<String> properties = propertyOwnerRepository.findPropertyIdsByUserId(user.getTin());
        return new UserDetailsDto(details, properties);
    }

}






