package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.domain.category.PropertyType;
import com.scytalys.technikon.dto.UserCreationDto;
import com.scytalys.technikon.dto.UserSearchDto;
import com.scytalys.technikon.dto.UserUpdateDto;
import com.scytalys.technikon.mapper.OwnerMapper;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.repository.PropertyRepository;
import java.util.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.dao.DataAccessResourceFailureException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
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

    @Spy
    private OwnerMapper ownerMapper = OwnerMapper.INSTANCE; // Initialize ownerMapper


    @Spy
    private PropertyOwner propertyOwner;

    private UserCreationDto dto;

    @Spy
    private Property property;

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
        PropertyOwner result =  propertyOwnerService.createDBUser(dto);
        assertEquals(propertyOwner, result);

    }

    /**
     * This test verifies the behavior of the searchUser method when the property owner is found in the DB.
     */
    @Test
    public void testSearchUserByTin(){

        when(propertyOwnerRepository.search(eq(propertyOwner.getUsername()), eq(propertyOwner.getEmail()), eq(propertyOwner.getTin()))).thenReturn(Optional.of(propertyOwner));
        UserSearchDto searchRequest=new UserSearchDto(propertyOwner.getTin(),propertyOwner.getUsername(),propertyOwner.getEmail());
        User result = propertyOwnerService.searchUser(searchRequest);
        assertEquals(propertyOwner, result);
    }

    /**
     * This test verifies the behavior of the searchUser method when the fields are null.
     */
    @Test
    public void testSearchUserByFail(){
        when(propertyOwnerRepository.search(eq(null),eq(null),eq(null))).thenReturn(Optional.ofNullable(null));
        UserSearchDto searchRequest=new UserSearchDto(null,null,null);
        User result =propertyOwnerService.searchUser(searchRequest);
        assertNull(result);
    }


    /**
     * This test verifies that the deleteUser method correctly returns when the repository operation is successful.
     */

    @Test
    public void testDeleteUser() {
        when(propertyOwnerRepository.deleteByTin(eq(propertyOwner.getTin()))).thenReturn(1);
        assertDoesNotThrow(() -> propertyOwnerService.deleteUser(propertyOwner.getTin()));
    }


    /**
     * This test verifies that the softDeleteUser method correctly sets the active status of a PropertyOwner to false.
     */
    @Test
    public void testSoftDeleteUser() {
        when(propertyOwnerRepository.save(eq(propertyOwner))).thenReturn(propertyOwner);
        propertyOwnerService.createDBUser(dto);
        doAnswer(invocation -> {
            propertyOwner.setActive(false); // Set isActive flag to false
            return 1;
        }).when(propertyOwnerRepository).softDeleteByTin(eq(propertyOwner.getTin()), eq(propertyOwner.getVersion()));
        propertyOwnerService.softDeleteUser(propertyOwner.getTin(),propertyOwner.getVersion());
        // Assert that isActive flag is set to false
        assertFalse(propertyOwner.isActive());
    }




    /**
     * This test verifies that the softDeleteUser throws DataAccessResourceFailure if the delete fails
     */
    @Test
    public  void testSoftDeleteUserFail() {
        propertyOwnerService.createDBUser(dto);
        assertThrows(DataAccessResourceFailureException.class, ()->propertyOwnerService.softDeleteUser(propertyOwner.getTin(), 0));

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

        // Stubbing the behavior of propertyOwnerRepository.update
        doAnswer(invocation -> {

                Long version=invocation.getArgument(4);

            // Check if the version matches
            if (propertyOwner.getVersion() == version ) {
                propertyOwner.setVersion(propertyOwner.getVersion() + 1);
                String tin = invocation.getArgument(0);
                String email = invocation.getArgument(1);
                String address = invocation.getArgument(2);
                String password = invocation.getArgument(3);
                return 1;
            }
            return 0;
        }).when(propertyOwnerRepository).update(any(String.class), any(String.class), any(String.class), any(String.class), any(Long.class));

        // Call the UpdateUser method with updateRequest
        propertyOwnerService.UpdateUser(updateRequest);

        // Assert that an exception is thrown when attempting to update with updateRequest2
        assertThrows(DataAccessResourceFailureException.class, () -> propertyOwnerService.UpdateUser(updateRequest2));
    }


}