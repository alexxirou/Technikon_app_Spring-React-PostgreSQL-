package com.scytalys.technikon.service.impl;


import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;


import com.scytalys.technikon.domain.User;

import com.scytalys.technikon.domain.category.PropertyType;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.repository.PropertyOwnerRepository;


import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.service.PropertyOwnerService;


import com.scytalys.technikon.service.PropertyService;
import jakarta.persistence.EntityNotFoundException;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;


import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PropertyOwnerServiceImplTest {

    @InjectMocks
    private PropertyOwnerServiceImpl propertyOwnerService;


    @Mock
    private PropertyOwnerRepository propertyOwnerRepository;

    @InjectMocks
    PropertyServiceImpl propertyService;
    @Mock
    PropertyRepository propertyRepository;



    private PropertyOwner propertyOwner;


    private Property property;

    @BeforeEach
    public void setup(){

        MockitoAnnotations.openMocks(this);

        propertyOwner= new PropertyOwner();
        propertyOwner.setId(3L); // id
        propertyOwner.setName("Johny"); // name
        propertyOwner.setSurname("Doep"); // surname
        propertyOwner.setEmail("jdee@hotmail.com"); // email
        propertyOwner.setUsername("jdee"); // username
        propertyOwner.setPassword("pass"); // password
        propertyOwner.setAddress("somewhere"); // address
        propertyOwner.setPhoneNumber("999582486");// phoneNumber
        propertyOwner.setVersion(0);


    }
    @AfterEach
    public void teardown(){

   
        propertyOwnerService.deleteUser(propertyOwner.getTin());
        propertyOwner=null;


    }

    /**
     * This test verifies that the createUser method correctly saves a PropertyOwner to the repository and returns the saved PropertyOwner.
     */
//    @Test
//    public void testCreateUser() {
//        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
//        //PropertyOwner result =  propertyOwnerService.createDBUser(propertyOwner);
////        System.out.println(result);
////        assertEquals(propertyOwner, result);
//
//    }
////
//    /**
//     * This test verifies the behavior of the searchUserById method when a PropertyOwner with the given ID is found.
//     */
//    @Test
//    public void testSearchUserById(){
//        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
//        when(propertyOwnerRepository.findById(any(Long.class))).thenReturn(Optional.of(propertyOwner));
//        propertyOwnerService.createDBUser(propertyOwner);
//        //User result = propertyOwnerService.searchUserById(propertyOwner.getId());
////        assertEquals(propertyOwner, result);
//    }
//
//    /**
//     * This test verifies the behavior of the searchUserById method when a PropertyOwner with the given ID is not found.
//     */
//    @Test
//    public void testSearchUserByIdFail(){
//
//        //User result =propertyOwnerService.searchUserById(0L);
////        assertThrows(EntityNotFoundException.class, () -> propertyOwnerService.verifySearchResult(result));
//    }
//
//    /**
//     * This test verifies the behavior of the searchUserByUsername method when a PropertyOwner with the given username is found.
//     */
//    @Test
//    public void testSearchUserByUsername(){
////        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
////        when(propertyOwnerRepository.findByUsername(any(String.class))).thenReturn(Optional.of(propertyOwner));
//        propertyOwner= (PropertyOwner) propertyOwnerService.createDBUser(propertyOwner);
//        //User result = propertyOwnerService.searchUserByUsername(propertyOwner.getUsername());
////        System.out.println(result);
////        assertEquals(propertyOwner, result);
//    }
//
//    /**
//     * This test verifies the behavior of the searchUserByUsername method when a PropertyOwner with the given Username is not found.
//     */
//    @Test
//    public void testSearchUserByUsernameFail(){
////        when(propertyOwnerRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
//        //User result=propertyOwnerService.searchUserByUsername("");
////        assertThrows(EntityNotFoundException.class, () -> propertyOwnerService.verifySearchResult(result));
//    }
//
//    /**
//     * This test verifies the behavior of the searchUserByEmail method when a PropertyOwner with the given email is found.
//     */
//    @Test
//    public void testSearchUserByEmail(){
////        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
////        when(propertyOwnerRepository.findByEmail(any(String.class))).thenReturn(Optional.of(propertyOwner));
//        propertyOwnerService.createDBUser(propertyOwner);
//        //User result = propertyOwnerService.searchUserByEmail(propertyOwner.getEmail());
////        assertEquals(propertyOwner, result);
//    }
//
//    /**
//     * This test verifies the behavior of the searchUserByEmail method when a PropertyOwner with the given email is not found.
//     */
//    @Test
//    public void testSearchUserByEmailFail(){
////        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
//        //User result =propertyOwnerService.searchUserByEmail("");
////        assertThrows(EntityNotFoundException.class, () -> propertyOwnerService.verifySearchResult(result));
//    }
//
//    /**
//     * This test verifies that the updateUserEmail method correctly updates the email of a PropertyOwner.
//     */
//    @Test
//
//
//    public void testUpdateUserEmail()  {
//
//        PropertyOwner user2=(PropertyOwner) propertyOwnerService.createDBUser(propertyOwner);
//        String newEmail = "newEmail@example.com";
//        //propertyOwnerService.updateUserEmail(user2.getId(), newEmail, user2.getVersion());
//
//        // Flush changes to the database
//        //entityManager.flush();
//
//        //User result=propertyOwnerService.searchUserByEmail(newEmail.toLowerCase());
//
////        System.out.println(result);
////        assertEquals(newEmail.toLowerCase(), result.getEmail());
//    }
//
//    /**
//     * This test verifies that the UpdateUserEmail fails because there is a unique constraint violation.
//     */
//    @Test
//    public void testUpdateUserEmailFail() {
////        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
//        //PropertyOwner user=(PropertyOwner) propertyOwnerService.createDBUser(propertyOwner);
//        PropertyOwner propertyOwner2 = new PropertyOwner();
//        propertyOwner2.setId(4L); // id
//        propertyOwner2.setName("John"); // name
//        propertyOwner2.setSurname("Doe"); // surname
//        propertyOwner2.setEmail("newEmail@example.com"); // email
//        propertyOwner2.setUsername("jde2"); // username
//        propertyOwner2.setPassword("pass"); // password
//        propertyOwner2.setAddress("somewhere"); // address
//        propertyOwner2.setPhoneNumber("999582486");
//        String newEmail = "newEmail@example.com";
//        //propertyOwnerService.updateUserEmail(user.getId(), newEmail, user.getVersion());
////        assertNotEquals(newEmail.toLowerCase(), user.getEmail());
//    }
//
//    /**
//     * This test verifies that the updateUserAddress method correctly updates the address of a PropertyOwner.
//     */
//    @Test
//    public void testUpdateUserAddress() {
//        //when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
//        PropertyOwner user= (PropertyOwner) propertyOwnerService.createDBUser(propertyOwner);
//        String newAddress = "New Address";
////        propertyOwnerService.updateUserAddress(propertyOwner.getId(), newAddress, propertyOwner.getVersion());
////        PropertyOwner result = (PropertyOwner) propertyOwnerService.searchUserById(user.getId());
////        assertEquals(newAddress, result.getAddress());
//    }
//
//    /**
//     * This test verifies that the updateUserPassword method correctly updates the password of a PropertyOwner.
//     */
//    @Test
//    public void testUpdateUserPassword() {
//        //when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
//        PropertyOwner user= (PropertyOwner) propertyOwnerService.createDBUser(propertyOwner);
//        String newPassword = "newPassword";
////        propertyOwnerService.updateUserPassword(user.getId(), newPassword, user.getVersion());
////        PropertyOwner result = (PropertyOwner) propertyOwnerService.searchUserById(user.getId());
////        assertEquals(newPassword, result.getPassword());
//    }
//
//    /**
//     * This test verifies that the deleteUser method correctly deletes a PropertyOwner from the repository.
//     */
//    @Test
//    public void testDeleteUser() {
//        //when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
//        propertyOwnerService.createDBUser(propertyOwner);
//        long id = propertyOwner.getId();
//        propertyOwnerService.deleteUser(id);
//        //when(propertyOwnerRepository.findById(any(Long.class))).thenReturn(Optional.empty());
//        Optional<PropertyOwner> deletedUser = propertyOwnerRepository.findById(id);
//        assertFalse(deletedUser.isPresent());
//
//    }
//
//    /**
//     * This test verifies that the softDeleteUser method correctly sets the active status of a PropertyOwner to false.
//     */
//    @Test
//    public void testSoftDeleteUser() {
//
//
////            when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
//
//        // Create a user
//        propertyOwnerService.createDBUser(propertyOwner);
//
//        // Call the softDeleteUser method
//        propertyOwnerService.softDeleteUser(propertyOwner.getId(), propertyOwner.getVersion());
//
//        // Fetch the entity from the repository
//        PropertyOwner deletedUserOptional = propertyOwnerRepository.findById(propertyOwner.getId()).orElse(null);
//
//
//        System.out.println(deletedUserOptional);
//        // Assert that isActive flag is set to false
//        assertFalse(deletedUserOptional.isActive());
//    }
//
//
//
//
//    /**
//     * This test verifies that the softDeleteUser method returns null if row is non-existent.
//     */
//    @Test
//    public  void testSoftDeleteUserFail() {
//        //when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
//        propertyOwnerService.createDBUser(propertyOwner);
//        int res =propertyOwnerService.softDeleteUser(6, 0);
//        assertEquals(res, 0);
//    }
//
//    /**
//     * This test verifies that a softDeleted user will not appear on searches
//     */
//
//    @Test void findSoftDeletedUser(){
//        propertyOwnerService.createDBUser(propertyOwner);
//        int res =propertyOwnerService.softDeleteUser(propertyOwner.getId(), propertyOwner.getVersion());
//        // User result =propertyOwnerService.searchUserById(3L);
////        assertNull(result);
//    }
//
//    /**
//     * This test verifies that the UserResponseDto is properly created from a User object.
//     */
//    @Test
//    public void testCreateUserResponseDto() {
//        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
//        UserResponseDto responseDto = propertyOwnerService.createUserResponseDto(propertyOwner);
//        assertEquals(propertyOwner.getId(), responseDto.id());
//        assertEquals(propertyOwner.getUsername(), responseDto.username());
//        assertEquals(propertyOwner.getEmail(), responseDto.email());
//    }
//
//
//
//    /**
//     * This test verifies that the verifyConstraintsId method throws a DataIntegrityViolationException when a PropertyOwner with the given ID is found.
//     */
//
//    /**
//     * Test that returns the propertyIds for a user that has properties
//     */
//    @Test
//    public void findPropertiesforUserFail(){
//        propertyOwnerService.createDBUser(propertyOwner);
//        PropertyOwner user = (PropertyOwner) propertyOwnerService.searchUser(propertyOwner);
//        boolean res= propertyOwnerService.checkUserHasProperties(propertyOwner.getId());
//        assertTrue(res,"The properties list should be empty.");
//
//
//    }
//
//    /**
//     * Test that returns the propertyIds for a user that has properties
//     */
//    @Test
//    public void findPropertiesforUser(){
//        propertyOwnerService.createDBUser(propertyOwner);
//        PropertyOwner user = (PropertyOwner) propertyOwnerService.searchUser(propertyOwner);
//        Property property = new Property();
//        property.setId(1L);
//        property.setAddress("somewhere");
//        property.setPropertyType(PropertyType.values()[1]);
//        property.setLatitude(10.5);
//        property.setLongitude(58.4);
//        property.setPropertyOwner(propertyOwner);
//
//        propertyService.createProperty(property);
////        List<Long> properties= propertyOwnerService.findPropertiesForUser(propertyOwner.getId());
////        assertEquals(1, properties.size());
////        assertEquals(1L, properties.get(0));
//        propertyRepository.delete(property);
//
//    }
//
//
//    /**
//     * Test that returns true if users have properties
//     */
//    @Test
//    public void testFindUsersWithProperties(){
//        propertyOwnerService.createDBUser(propertyOwner);
//        PropertyOwner user = (PropertyOwner) propertyOwnerService.searchUser(propertyOwner);
//        Property property = new Property();
//        property.setId(1L);
//        property.setAddress("somewhere");
//        property.setPropertyType(PropertyType.values()[1]);
//        property.setLatitude(10.5);
//        property.setLongitude(58.4);
//        property.setPropertyOwner(propertyOwner);
//
//        propertyService.createProperty(property);
//  //      List<Long> properties= propertyOwnerService.findPropertiesForUser(propertyOwner.getId());
//    //    boolean result = propertyOwnerService.checkUserHasProperties(properties);
//    //    assertTrue(result, "Result should be true because user has a property.");
//        propertyRepository.delete(property);
//
//    }
//
//
//    /**
//     * Test that returns false if users do not  have properties
//     */
//    @Test
//    public void testFindUsersWithPropertiesFail(){
//        propertyOwnerService.createDBUser(propertyOwner);
//    //    List<Long> properties= propertyOwnerService.findPropertiesForUser(propertyOwner.getId());
//     //   boolean result = propertyOwnerService.checkUserHasProperties(properties);
//     //   assertFalse(result, "Result should be false because the property is not linked to the user.");
//
//
//    }
//
//    /**
//     * Concurrency test with two threads updating the password in the db at the same time.
//     * Checks optimistic locking through version works by tracking the Exception thrown on version mismatch.
//     *
//     */
//    @Test
//    public void whenConcurrentUpdate_thenThrowException() throws InterruptedException {
//
////         when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
//
//
//        propertyOwnerService.createDBUser(propertyOwner);
//
//        AtomicBoolean exceptionThrown = new AtomicBoolean(false);
//
//    //    Thread thread1 = new Thread(() -> { propertyOwnerService.updateUserPassword(propertyOwner.getId(),"new", propertyOwner.getVersion());});
//
//        Thread thread2 = new Thread(() -> {
//            try {
//                Thread.sleep(100);
//
//      //          int res = propertyOwnerService.updateUserPassword(propertyOwner.getId(),"newer", propertyOwner.getVersion() );
//                //          if (res==0) throw new OptimisticLockingFailureException("Resource is busy.");
//
//            } catch (OptimisticLockingFailureException e  ) {
//                e.printStackTrace();
//                exceptionThrown.set(true);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        //thread1.start();
//        thread2.start();
//        //thread1.join();
//        thread2.join();
//
//
//
//        assertTrue(exceptionThrown.get(), "Expected OptimisticLockingFailureException to be thrown, but it wasn't");
//    }

}