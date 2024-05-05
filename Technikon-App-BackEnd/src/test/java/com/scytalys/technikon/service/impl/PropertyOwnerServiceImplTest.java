package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.PropertyOwner;


import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.repository.PropertyOwnerRepository;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;


import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


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
        propertyOwner.setEmail("jde@hotmail.com"); // email
        propertyOwner.setUsername("jde"); // username
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
        assertThrows(EntityNotFoundException.class, () -> propertyOwnerService.verifySearchResult(result));
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
        assertThrows(EntityNotFoundException.class, () -> propertyOwnerService.verifySearchResult(result));
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
        assertThrows(EntityNotFoundException.class, () -> propertyOwnerService.verifySearchResult(result));
    }

    /**
     * This test verifies that the updateUserEmail method correctly updates the email of a PropertyOwner.
     */
    @Test
    public void testUpdateUserEmail() {
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        propertyOwnerService.createUser(propertyOwner);
        String newEmail = "newEmail@example.com";
        propertyOwnerService.updateUserEmail(propertyOwner.getId(), newEmail, propertyOwner.getVersion());
        assertEquals(newEmail.toLowerCase(), propertyOwner.getEmail());
    }

    /**
     * This test verifies that the updateUserAddress method correctly updates the address of a PropertyOwner.
     */
    @Test
    public void testUpdateUserAddress() {
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        propertyOwnerService.createUser(propertyOwner);
        String newAddress = "New Address";
        propertyOwnerService.updateUserAddress(propertyOwner.getId(), newAddress, propertyOwner.getVersion());
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
        propertyOwnerService.updateUserPassword(propertyOwner.getId(), newPassword, propertyOwner.getVersion());
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
        propertyOwnerService.softDeleteUser(propertyOwner.getId(), propertyOwner.getVersion());
        Assertions.assertFalse(propertyOwner.isActive());
    }

    /**
     * This test verifies that the softDeleteUser method returns null if row is non existant.
     */
    @Test
    public void testSoftDeleteUserfail() {
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        propertyOwnerService.createUser(propertyOwner);
        propertyOwner= propertyOwnerService.softDeleteUser(6, 0);
        assertNull(propertyOwner);
    }

    /**
     * This test verifies that the UserResponseDto is properly created from a User object.
     */
    @Test
    public void testCreateUserResponseDto() {
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        propertyOwnerService.createUser(propertyOwner);
        UserResponseDto responseDto = propertyOwnerService.createUserResponseDto(propertyOwner.getId(), propertyOwner.getUsername(), propertyOwner.getEmail(), propertyOwner.getVersion());
        assertEquals(propertyOwner.getId(), responseDto.id());
        assertEquals(propertyOwner.getUsername(), responseDto.username());
        assertEquals(propertyOwner.getEmail(), responseDto.email());
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

    @Test
    public void whenConcurrentUpdate_thenThrowException() throws InterruptedException {
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        when(propertyOwnerRepository.findById(any(Long.class))).thenReturn(Optional.of(propertyOwner));


        propertyOwnerService.createUser(propertyOwner);

        PropertyOwner propertyOwner2 = new PropertyOwner();
        propertyOwner2.setId(2L); // id
        propertyOwner2.setName("John"); // name
        propertyOwner2.setSurname("Doe"); // surname
        propertyOwner2.setEmail("jde@hotmail.com"); // email
        propertyOwner2.setUsername("jde"); // username
        propertyOwner2.setPassword("pass"); // password
        propertyOwner2.setAddress("somewhere"); // address
        propertyOwner2.setPhoneNumber("999582486");
        AtomicBoolean exceptionThrown = new AtomicBoolean(false);



        Thread thread1 = new Thread(() -> { propertyOwnerService.updateUserPassword(propertyOwner.getId(),"new", propertyOwner.getVersion()); System.out.println("User1 " +propertyOwner.getVersion()); });

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(100);
                System.out.println("User2 " +propertyOwner2.getVersion());
                User user2 = propertyOwnerService.updateUserPassword(propertyOwner2.getId(),"newer", propertyOwner2.getVersion() );
                if (user2==null) throw new OptimisticLockingFailureException("Resource is busy.");

            } catch (OptimisticLockingFailureException e  ) {
                e.printStackTrace();
                exceptionThrown.set(true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();



        assertTrue(exceptionThrown.get(), "Expected OptimisticLockingFailureException to be thrown, but it wasn't");
    }

}