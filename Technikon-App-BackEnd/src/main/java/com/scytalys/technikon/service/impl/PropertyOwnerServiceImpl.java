package com.scytalys.technikon.service.impl;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserUpdateDto;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.dto.UserResponseToSearchDto;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
@AllArgsConstructor
public class PropertyOwnerServiceImpl implements UserService {

    private final PropertyOwnerRepository propertyOwnerRepository;

    /**
     *
     * @param propertyOwner
     * @return PropertyOwner
     */
    @Override
    public User createUser(User propertyOwner) {
        return propertyOwnerRepository.save((PropertyOwner) propertyOwner);
    }

    /**
     *
     * @param id
     * @return PropertyOwner
     */
    @Override
    public User searchUser(long id) {
        return propertyOwnerRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("User Id not found."));
    }

    /**
     *
     * @param username
     * @return propertyOwner
     */
    @Override
    public User searchUser(String username) {
        return propertyOwnerRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("Username not found."));
    }

    /**
     *
     * @param propertyOwnerUpdateDto
     */

    public void updateUser(UserUpdateDto propertyOwnerUpdateDto) {
        propertyOwnerRepository.updatePropertyOwnerById
                        (propertyOwnerUpdateDto.id(),
                        propertyOwnerUpdateDto.email(),
                        propertyOwnerUpdateDto.address(),
                        propertyOwnerUpdateDto.password());

    }

    /**
     *
     * @param propertyOwnerId
     */
    public void deleteUser(long propertyOwnerId) {
        propertyOwnerRepository.deleteById(propertyOwnerId);
    }

    /**
     *
     * @param propertyOwnerId
     */
    public void softDeleteUser(long propertyOwnerId){
        propertyOwnerRepository.softDeleteById(propertyOwnerId);
    }

    /**
     *
     * @param user
     * @param errorMessage
     * @return UserResponseDto
     */

    public UserResponseDto createUserCreationDto(User user, String errorMessage){
        return new UserResponseDto(user, errorMessage);

    }

    /**
     *
     * @param id
     * @param username
     * @param email
     * @param errorMessage
     * @return UserResponseToSearchDto
     */

    public UserResponseToSearchDto createUserSearchDto(long id, String username, String email, String errorMessage){
       return new UserResponseToSearchDto(
               id,
               username,
               email,
               errorMessage);

    }

    /**
     *
     * @param id
     * @param password
     * @param email
     * @param address
     * @param errorMessage
     * @return UserUpdateDto
     */
    public UserUpdateDto createUpdateUserDto(long id, String password, String email, String address, String errorMessage ){
        return new UserUpdateDto(id, address, email, password, errorMessage);
    }
}


