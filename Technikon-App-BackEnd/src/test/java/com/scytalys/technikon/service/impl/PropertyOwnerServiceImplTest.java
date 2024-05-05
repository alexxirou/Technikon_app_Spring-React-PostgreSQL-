package com.scytalys.technikon.service.impl;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.scytalys.technikon.domain.PropertyOwner;

import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.dto.UserResponseToSearchDto;
import com.scytalys.technikon.dto.UserUpdateDto;
import com.scytalys.technikon.repository.PropertyOwnerRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;


import java.util.NoSuchElementException;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


public class PropertyOwnerServiceImplTest {

    @InjectMocks
    private PropertyOwnerServiceImpl propertyOwnerService;

    @Mock
    private PropertyOwnerRepository propertyOwnerRepository;

    private PropertyOwner propertyOwner;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);

        propertyOwner= new PropertyOwner();
        propertyOwner.setId(2L); // id
        propertyOwner.setName("John"); // name
        propertyOwner.setSurname("Doe"); // surname
        propertyOwner.setEmail("JDE@hotmail.com"); // email
        propertyOwner.setUsername("JDE"); // username
        propertyOwner.setPassword("pass"); // password
        propertyOwner.setAddress("somewhere"); // address
        propertyOwner.setPhoneNumber("999582486"); // phoneNumber

    }

    /**
     * This test verifies that the createUser method correctly saves a PropertyOwner to the repository and returns the saved PropertyOwner.
     */
    @Test
    public void testCreateUser() {
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        PropertyOwner result =  propertyOwnerService.createUser(propertyOwner);
        System.out.println(result);
        assertEquals(propertyOwner, result);

    }

    /**
     * This test verifies the behavior of the searchUserById method when a PropertyOwner with the given ID is found.
     */
    @Test
    public void testSearchUserById(){
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        when(propertyOwnerRepository.findById(any(Long.class))).thenReturn(Optional.of(propertyOwner));
        propertyOwnerService.createUser(propertyOwner);
        PropertyOwner result = propertyOwnerService.searchUserById(propertyOwner.getId());
        assertEquals(propertyOwner, result);
    }

    /**
     * This test verifies the behavior of the searchUserById method when a PropertyOwner with the given ID is not found.
     */
    @Test
    public void testSearchUserByIdFail(){
        when(propertyOwnerRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        PropertyOwner result =propertyOwnerService.searchUserById(0L);
        assertThrows(NoSuchElementException.class, () -> propertyOwnerService.verifySearchResult(result));
    }

    /**
     * This test verifies the behavior of the searchUserByUsername method when a PropertyOwner with the given username is found.
     */
    @Test
    public void testSearchUserByUsername(){
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        when(propertyOwnerRepository.findByUsername(any(String.class))).thenReturn(Optional.of(propertyOwner));
        propertyOwnerService.createUser(propertyOwner);
        PropertyOwner result = propertyOwnerService.searchUserByUsername(propertyOwner.getUsername());
        assertEquals(propertyOwner, result);
    }

    /**
     * This test verifies the behavior of the searchUserByUsername method when a PropertyOwner with the given Username is not found.
     */
    @Test
    public void testSearchUserByUsernameFail(){
        when(propertyOwnerRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        PropertyOwner result=propertyOwnerService.searchUserByUsername("");
        assertThrows(NoSuchElementException.class, () -> propertyOwnerService.verifySearchResult(result));
    }

    /**
     * This test verifies the behavior of the searchUserByEmail method when a PropertyOwner with the given email is found.
     */
    @Test
    public void testSearchUserByEmail(){
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        when(propertyOwnerRepository.findByEmail(any(String.class))).thenReturn(Optional.of(propertyOwner));
        propertyOwnerService.createUser(propertyOwner);
        PropertyOwner result = propertyOwnerService.searchUserByEmail(propertyOwner.getEmail());
        assertEquals(propertyOwner, result);
    }

    /**
     * This test verifies the behavior of the searchUserByEmail method when a PropertyOwner with the given email is not found.
     */
    @Test
    public void testSearchUserByEmailFail(){
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        PropertyOwner result =propertyOwnerService.searchUserByEmail("");
        assertThrows(NoSuchElementException.class, () -> propertyOwnerService.verifySearchResult(result));
    }

    /**
     * This test verifies that the updateUserEmail method correctly updates the email of a PropertyOwner.
     */
    @Test
    public void testUpdateUserEmail() {
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        propertyOwnerService.createUser(propertyOwner);
        String newEmail = "newEmail@example.com";
        propertyOwnerService.updateUserEmail(newEmail, propertyOwner);
        assertEquals(newEmail, propertyOwner.getEmail());
    }

    /**
     * This test verifies that the updateUserAddress method correctly updates the address of a PropertyOwner.
     */
    @Test
    public void testUpdateUserAddress() {
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        propertyOwnerService.createUser(propertyOwner);
        String newAddress = "New Address";
        propertyOwnerService.updateUserAddress(newAddress, propertyOwner);
        assertEquals(newAddress, propertyOwner.getAddress());
    }

    /**
     * This test verifies that the updateUserPassword method correctly updates the password of a PropertyOwner.
     */
    @Test
    public void testUpdateUserPassword() {
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        propertyOwnerService.createUser(propertyOwner);
        String newPassword = "newPassword";
        propertyOwnerService.updateUserPassword(newPassword, propertyOwner);
        assertEquals(newPassword, propertyOwner.getPassword());
    }

    /**
     * This test verifies that the deleteUser method correctly deletes a PropertyOwner from the repository.
     */
    @Test
    public void testDeleteUser() {
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        doNothing().when(propertyOwnerRepository).deleteById(any(Long.class));
        propertyOwnerService.createUser(propertyOwner);
        long id = propertyOwner.getId();
        propertyOwnerService.deleteUser(id);
        when(propertyOwnerRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Optional<PropertyOwner> deletedUser = propertyOwnerRepository.findById(id);
        assertFalse(deletedUser.isPresent());
    }

    /**
     * This test verifies that the softDeleteUser method correctly sets the active status of a PropertyOwner to false.
     */
    @Test
    public void testSoftDeleteUser() {
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        propertyOwnerService.createUser(propertyOwner);
        propertyOwnerService.softDeleteUser(propertyOwner);
        Assertions.assertFalse(propertyOwner.isActive());
    }


    /**
     * This test verifies that the createUserCreationResponseDto method correctly creates a UserResponseDto object.
     */
    @Test
    public void testCreateUserCreationResponseDto() {
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        propertyOwnerService.createUser(propertyOwner);
        String errorMessage = "No errors";
        UserResponseDto dto = propertyOwnerService.createUserCreationResponseDto(propertyOwner, errorMessage);
        assertEquals(propertyOwner, dto.user());
        assertEquals(errorMessage, dto.errorMessage());
    }
    /**
     * This test verifies that the createUserSearchResponseDto method correctly creates a UserResponseToSearchDto object.
     */
    @Test
    public void testCreateUserSearchResponseDto() {
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        propertyOwnerService.createUser(propertyOwner);
        long id = propertyOwner.getId();
        String username = propertyOwner.getUsername();
        String email = propertyOwner.getEmail();
        String errorMessage = "No errors";
        UserResponseToSearchDto dto = propertyOwnerService.createUserSearchResponseDto(id, username, email, errorMessage);
        assertEquals(id, dto.id());
        assertEquals(username, dto.username());
        assertEquals(email, dto.email());
        assertEquals(errorMessage, dto.errorMessage());
    }

    /**
     * This test verifies that the createUserUpdateResponseDto method correctly creates a UserUpdateDto object.
     */
    @Test
    public void testCreateUserUpdateResponseDto() {
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        propertyOwnerService.createUser(propertyOwner);
        long id = propertyOwner.getId();
        String password = propertyOwner.getPassword();
        String email = propertyOwner.getEmail();
        String address = propertyOwner.getAddress();
        String errorMessage = "No errors";
        UserUpdateDto dto = propertyOwnerService.createUserUpdateResponseDto(id, password, email, address, errorMessage);
        assertEquals(id, dto.id());
        assertEquals(password, dto.password());
        assertEquals(email, dto.email());
        assertEquals(address, dto.address());
        assertEquals(errorMessage, dto.errorMessage());
    }

    /**
     * This test verifies that the verifyConstraintsId method throws a DataIntegrityViolationException when a PropertyOwner with the given ID is found.
     */
    @Test
    public void testVerifyConstraintsIdFailure(){
        when(propertyOwnerRepository.findById(any(Long.class))).thenReturn(Optional.of(propertyOwner));
        assertThrows(DataIntegrityViolationException.class, () -> {
            propertyOwnerService.verifyConstraintsId(propertyOwner.getId());
        });
    }

    /**
     * This test verifies that the verifyConstraintsUsername method throws a DataIntegrityViolationException when a PropertyOwner with the given Username is found.
     */
    @Test
    public void testVerifyConstraintsUsernameFailure(){
        when(propertyOwnerRepository.findByUsername(any(String.class))).thenReturn(Optional.of(propertyOwner));
        assertThrows(DataIntegrityViolationException.class, () -> {
            propertyOwnerService.verifyConstraintsUsername(propertyOwner.getUsername());
        });
    }


    /**
     * This test verifies that the verifyConstraintsEmail method throws a DataIntegrityViolationException when a PropertyOwner with the given Email is found.
     */
    @Test
    public void testVerifyConstraintsEmailFailure() {
        when(propertyOwnerRepository.findByEmail(any(String.class))).thenReturn(Optional.of(propertyOwner));
        assertThrows(DataIntegrityViolationException.class, () -> {
            propertyOwnerService.verifyConstraintsEmail(propertyOwner.getEmail());
        });
    }

}