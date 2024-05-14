package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.domain.category.PropertyType;
import com.scytalys.technikon.dto.*;
import com.scytalys.technikon.mapper.OwnerMapper;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.repository.PropertyRepository;

import java.sql.Array;
import java.util.*;

import com.scytalys.technikon.security.service.UserInfoService;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class PropertyOwnerServiceImplTest {


    @Mock
    private PropertyOwnerRepository propertyOwnerRepository;
    @Mock
    PropertyRepository propertyRepository;

    @Spy
    @InjectMocks
    PropertyServiceImpl propertyService;
    @Spy
    @InjectMocks
    private PropertyOwnerServiceImpl propertyOwnerService;
    @Spy
    @InjectMocks
    private UserInfoService userInfoService;

    @Spy
    private OwnerMapper ownerMapper = OwnerMapper.INSTANCE; // Initialize ownerMapper
    @Spy
    private PropertyOwner propertyOwner;

    private UserCreationDto dto;



    @BeforeEach
    public void setup(){

        MockitoAnnotations.openMocks(this);

        propertyOwner= new PropertyOwner();; // id
        propertyOwner.setTin("1651614865GR");
        propertyOwner.setName("Johny"); // name
        propertyOwner.setSurname("Doep"); // surname
        propertyOwner.setEmail("jdee@hotmail.com"); // email
        propertyOwner.setUsername("jdeeefe"); // username
        propertyOwner.setPassword("pass"); // password
        propertyOwner.setAddress("somewhere"); // address
        propertyOwner.setPhoneNumber("+30999582486");// phoneNumber
        propertyOwner.setVersion(0);
        dto =new UserCreationDto(propertyOwner.getTin(), propertyOwner.getName(), propertyOwner.getSurname(), propertyOwner.getEmail(), propertyOwner.getUsername(), propertyOwner.getPassword(), propertyOwner.getAddress(), propertyOwner.getPhoneNumber());



    }
    @AfterEach
    public void tearDown(){
        propertyOwner=null;
        dto=null;
    }

    /**
     * This test verifies that the createUser method correctly  the object when the repository method succeeds.
     */
    @Test
    public void testCreateUser() {
        when(propertyOwnerRepository.save(eq(propertyOwner))).thenReturn(propertyOwner);
        PropertyOwner result =  userInfoService.createDBUser(dto);
        assertEquals(propertyOwner, result);

    }

    /**
     * This test verifies the behavior of the searchUser method when the property owner is found in the DB.
     */
    @Test
    public void testSearchUserByTin(){
        UserSearchDto request =new UserSearchDto(propertyOwner.getTin(),propertyOwner.getUsername(),propertyOwner.getEmail());


        when(propertyOwnerRepository.findOne(any(Specification.class))).thenReturn(Optional.of(propertyOwner));
        PropertyOwner result = propertyOwnerService.searchUser(request);
        assertNotNull(result);
        assertEquals(propertyOwner.getTin(), result.getTin());
        assertEquals(propertyOwner.getUsername(),result.getUsername());
        assertEquals(propertyOwner.getName(),result.getName());
        assertEquals(propertyOwner.getSurname(),result.getSurname());
        assertEquals(propertyOwner.getAddress(),result.getAddress());
        assertEquals(propertyOwner.getPhoneNumber(),result.getPhoneNumber());
        assertEquals(propertyOwner.getEmail(),result.getEmail());
        assertEquals(propertyOwner.getVersion(),result.getVersion());
        assertEquals(propertyOwner.isActive(),result.isActive());


    }

    /**
     * This test verifies the behavior of the searchUser method when the fields are null.
     */
    @Test
    public void testSearchUserByFail(){
        when(propertyOwnerRepository.findOne(any(Specification.class))).thenReturn(Optional.ofNullable(null));
        UserSearchDto searchRequest=new UserSearchDto(null,null,null);
        assertThrows(EntityNotFoundException.class,()->propertyOwnerService.searchUser(searchRequest));
    }


    /**
     * This test verifies that the deleteUser method correctly returns when the repository operation is successful.
     */

    @Test
    public void testDeleteUser() {
        when(propertyOwnerRepository.findOne(any(Specification.class))).thenReturn(Optional.of(propertyOwner));
        assertDoesNotThrow(() -> propertyOwnerService.deleteUser(propertyOwner.getTin()));
    }


    /**
     * This test verifies that the softDeleteUser method correctly sets the active status of a PropertyOwner to false.
     */
    @Test
    public void testSoftDeleteUser() {
        when(propertyOwnerRepository.findOne(any(Specification.class))).thenReturn(Optional.of(propertyOwner));

        when(propertyOwnerRepository.save(eq(propertyOwner))).thenReturn(propertyOwner);
        userInfoService.createDBUser(dto);
        doAnswer(invocation -> {
            propertyOwner.setActive(false); // Set isActive flag to false
            return 1;
        }).when(propertyOwnerService).softDeleteUser(eq(propertyOwner.getTin()));
        propertyOwnerService.softDeleteUser(propertyOwner.getTin());
        // Assert that isActive flag is set to false
        assertFalse(propertyOwner.isActive());
    }




    /**
     * This test verifies that the softDeleteUser throws DataAccessResourceFailure if the delete fails
     */
    @Test
    public  void testSoftDeleteUserFail() {
        userInfoService.createDBUser(dto);
        assertThrows(EntityNotFoundException.class, ()->propertyOwnerService.softDeleteUser(propertyOwner.getTin()));

    }


    /**
     * Test that returns the propertyIds for a user that has properties
     */
    @Test
    public void findPropertiesForUser(){
        Property property = new Property();
        property.setId(1L);
        property.setTin("1516165161dg");
        property.setAddress("somewhere");
        property.setPropertyType(PropertyType.values()[1]);
        property.setLatitude(10.5);
        property.setLongitude(58.4);
        property.setPropertyOwner(propertyOwner);
        List<String> propertiesList = List.of(property.getTin());
        when(propertyOwnerRepository.findPropertyIdsByUserId(eq(propertyOwner.getTin()))).thenReturn(propertiesList);
        boolean res = propertyOwnerService.checkUserHasProperties(propertyOwner.getTin());
        assertTrue(res);
        propertyRepository.delete(property);

    }


    /**
     * Test that returns false if users do not  have properties
     */
    @Test
    public void testFindUsersWithPropertiesFail(){
        when(propertyOwnerRepository.findPropertyIdsByUserId(eq(propertyOwner.getTin()))).thenReturn(new ArrayList<>());
        boolean res = propertyOwnerService.checkUserHasProperties(propertyOwner.getTin());
        assertFalse(res);

    }

    /**
     *
     * Checks DataAccessResourceFailure is thrown on update operations where there is a version mismatch.
     *
     */
    @Test
    public void whenConcurrentUpdate_thenThrowException(){
        UserUpdateDto updateRequest = new UserUpdateDto(propertyOwner.getTin(), "dezfze@fezfez.com", propertyOwner.getAddress(), propertyOwner.getPassword(), 0);
        UserUpdateDto updateRequest2 = new UserUpdateDto(propertyOwner.getTin(), "dezdefze@fezfez.com", propertyOwner.getAddress(), propertyOwner.getPassword(), 0);
        when(propertyOwnerRepository.findOne(any(Specification.class))).thenReturn(Optional.of(propertyOwner));
        // Stubbing the behavior of propertyOwnerRepository.update
        doAnswer(invocation -> {



            // Check if the version matches
            if (propertyOwner.getVersion() == updateRequest.version() ) {
                propertyOwner.setVersion(propertyOwner.getVersion() + 1);
            }
            return null;
        }).when(propertyOwnerRepository).save(propertyOwner);

        // Call the UpdateUser method with updateRequest
        propertyOwnerService.UpdateUser(updateRequest);

        // Assert that an exception is thrown when attempting to update with updateRequest2
        assertThrows(OptimisticLockingFailureException.class, () -> propertyOwnerService.UpdateUser(updateRequest2));
    }


}