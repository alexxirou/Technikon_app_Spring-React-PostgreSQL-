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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
     * Creates a new user in the repository as a PropertyOwner type
     *
     * @param dto The dto containing information of the user to be created.
     * @return ResponseDto.
     * throws IllegalArgumentException if important fields are null;
     * throws DataIntegrityViolationException if unique field constraint violation
     */
    @CacheEvict(value = "PropertyOwners", allEntries = true)
    @Override
    @Transactional
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
    public void UpdateUser(String tin, UserUpdateDto dto){

        PropertyOwner user= propertyOwnerRepository.findByTin(tin).orElseThrow(() -> new EntityNotFoundException("User not found."));
        if(user.getVersion()!= dto.version()) throw new OptimisticLockingFailureException("User cannot be updated, please try again later.");
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
    public void deleteUser(String tin) {
        PropertyOwner user= propertyOwnerRepository.findByTin(tin).orElseThrow(() -> new EntityNotFoundException("User not found."));
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

}






