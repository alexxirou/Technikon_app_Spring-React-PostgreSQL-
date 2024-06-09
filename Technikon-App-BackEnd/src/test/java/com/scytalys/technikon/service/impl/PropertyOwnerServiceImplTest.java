package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;

import com.scytalys.technikon.domain.category.PropertyType;
import com.scytalys.technikon.dto.user.*;

import com.scytalys.technikon.mapper.OwnerMapperImpl;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.repository.PropertyRepository;



import java.util.*;

import com.scytalys.technikon.security.service.UserInfoService;
import jakarta.persistence.EntityNotFoundException;

import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;


import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;



import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


public class PropertyOwnerServiceImplTest {

    @Rule
    public MockitoRule mockitoRule= MockitoJUnit.rule();

    @Spy
    OwnerMapperImpl ownerMapper;
    @Mock
    private PropertyOwnerRepository propertyOwnerRepository;
    @Mock
    PropertyRepository propertyRepository;


    @InjectMocks
    PropertyServiceImpl propertyService;

    @InjectMocks
    private PropertyOwnerServiceImpl propertyOwnerService;

    @InjectMocks
    private UserInfoService userInfoService;

    // Initialize ownerMapper
    @Mock
    private PropertyOwner propertyOwner;
    @Mock
    private Authentication authentication;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserCreationDto dto;



    @BeforeEach
    public void setup(){

        MockitoAnnotations.openMocks(this);

        propertyOwner= new PropertyOwner();
        propertyOwner.setId(1L);
        propertyOwner.setTin("1651614865GR");
        propertyOwner.setName("Johny"); // name
        propertyOwner.setSurname("Doep"); // surname
        propertyOwner.setEmail("jdee@hotmail.com"); // email
        propertyOwner.setUsername("jdeeefe"); // username
        propertyOwner.setPassword("pass"); // password
        propertyOwner.setAddress("somewhere"); // address
        propertyOwner.setPhoneNumber("+30999582486");// phoneNumber
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
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("pass");
        UserCreationDto newUser= new UserCreationDto(propertyOwner.getTin(),propertyOwner.getName(),propertyOwner.getSurname(),propertyOwner.getEmail(),propertyOwner.getUsername(), propertyOwner.getPassword(), propertyOwner.getAddress(),propertyOwner.getPhoneNumber());
        PropertyOwner result =  userInfoService.createDBUser(newUser);
        assertEquals(result.getTin(), propertyOwner.getTin());
        assertEquals(result.getName(), propertyOwner.getName());
        assertEquals(result.getSurname(), propertyOwner.getSurname());
        assertEquals(result.getEmail(), propertyOwner.getEmail());
        assertEquals(result.getUsername(), propertyOwner.getUsername());
        assertEquals(result.getPassword(), propertyOwner.getPassword());
        assertEquals(result.getAddress(), propertyOwner.getAddress());
        assertEquals(result.getPhoneNumber(), propertyOwner.getPhoneNumber());


    }

    /**
     * This test verifies the behavior of the searchUser method when the property owner is found in the DB.
     */
    @Test
    public void testSearchUserByTin(){
        UserSearchDto request =new UserSearchDto(propertyOwner.getTin(),propertyOwner.getUsername(),propertyOwner.getEmail());


        when(propertyOwnerRepository.findAll(any(Specification.class))).thenReturn(Optional.of(List.of(propertyOwner)));
        List<PropertyOwner> result = propertyOwnerService.searchUser(request);
        assertFalse(result.isEmpty());
        assertEquals(propertyOwner.getTin(), result.get(0).getTin());
        assertEquals(propertyOwner.getUsername(),result.get(0).getUsername());
        assertEquals(propertyOwner.getName(),result.get(0).getName());
        assertEquals(propertyOwner.getSurname(),result.get(0).getSurname());
        assertEquals(propertyOwner.getAddress(),result.get(0).getAddress());
        assertEquals(propertyOwner.getPhoneNumber(),result.get(0).getPhoneNumber());
        assertEquals(propertyOwner.getEmail(),result.get(0).getEmail());

        assertEquals(propertyOwner.isActive(),result.get(0).isActive());


    }

    /**
     * This test verifies the behavior of the searchUser method when the fields are null.
     */
    @Test
    public void testSearchUserByFail(){
        when(propertyOwnerRepository.findAll(any(Specification.class))).thenReturn(Optional.of(List.of()));
        UserSearchDto request=new UserSearchDto(null,null,null);
        List<PropertyOwner> result = propertyOwnerService.searchUser(request);
        assertTrue(result.isEmpty());
    }





    /**
     * This test verifies that the softDeleteUser method correctly sets the active status of a PropertyOwner to false.
     */
    @Test
    public void testSoftDeleteUser() {
        when(propertyOwnerRepository.findById(eq(1L))).thenReturn(Optional.of(propertyOwner));
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenAnswer(invocation -> invocation.getArgument(0));
        // Act: Call the method under test
        propertyOwnerService.softDeleteUser(propertyOwner.getId());

        // Assert: Verify the property owner was set to inactive

        assertFalse(propertyOwner.isActive());

    }




    /**
     * This test verifies that the softDeleteUser throws DataAccessResourceFailure if the delete fails
     */
    @Test
    public  void testSoftDeleteUserFail() {
        assertThrows(EntityNotFoundException.class, ()->propertyOwnerService.softDeleteUser(propertyOwner.getId()));

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
        when(propertyOwnerRepository.findPropertyIdsByUserId(eq(propertyOwner.getId()))).thenReturn(propertiesList);
        boolean res = propertyOwnerService.checkUserHasProperties(propertyOwner.getId());
        assertTrue(res);
        propertyRepository.delete(property);

    }


    /**
     * Test that returns false if users do not  have properties
     */
    @Test
    public void testFindUsersWithPropertiesFail(){
        when(propertyOwnerRepository.findPropertyIdsByUserId(eq(propertyOwner.getId()))).thenReturn(new ArrayList<>());
        boolean res = propertyOwnerService.checkUserHasProperties(propertyOwner.getId());
        assertFalse(res);

    }



    @Test
    void testUpdateUser() {

        UserUpdateDto updateRequest = new UserUpdateDto("dezfze@fezfez.com", "newAddress", "newPassword");

        // Mock the behavior of findById to return a PropertyOwner when called with any Long value
        when(propertyOwnerRepository.findById(any(Long.class))).thenReturn(Optional.of(propertyOwner));

        doAnswer(invocation -> {

            propertyOwner.setAddress(updateRequest.address());
            propertyOwner.setEmail(updateRequest.email());
            propertyOwner.setPassword(updateRequest.password());
            // Return any desired result, such as null
            return null;
        }).when(propertyOwnerRepository).save(any(PropertyOwner.class));
        // Mock the behavior of passwordEncoder.encode to return a non-null value when invoked
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");

        // Assert: Ensure the email and other fields have been updated correctly
        propertyOwnerService.updateUser(propertyOwner.getId(),updateRequest);
        assertEquals(updateRequest.email(), propertyOwner.getEmail());
        assertEquals(updateRequest.address(), propertyOwner.getAddress());
        assertEquals(updateRequest.password(), propertyOwner.getPassword());
    }

    /**
     * This method tests the updateUser throws an exception if the email is not valid.
     */
    @Test
    void testUpdateUserFailWrongEmailFormat(){
        UserUpdateDto updateRequest = new UserUpdateDto( "dezfzeezfezom", propertyOwner.getAddress(), propertyOwner.getPassword());
        when(propertyOwnerRepository.findById(any(Long.class))).thenReturn(Optional.of(propertyOwner));

        doAnswer(invocation -> {



            // Check if the version matches
                propertyOwner.setAddress(updateRequest.address());
                propertyOwner.setEmail(updateRequest.email());
                propertyOwner.setPassword(updateRequest.password());
            return null;
        }).when(propertyOwnerRepository).save(propertyOwner);

        assertThrows(IllegalArgumentException.class, () -> propertyOwnerService.updateUser(propertyOwner.getId(),updateRequest));


    }
    /**
     * Test the userDetails method when a property owner has properties.
     * This test verifies that the userDetails method returns the expected UserDetails object
     * with the correct user details and properties when a property owner exists.
     */
    @Test
    void userDetails_ReturnsUserDetails_WhenPropertyOwnerExists() {

        UserSearchResponseDto userDto = new UserSearchResponseDto( propertyOwner.getId(),propertyOwner.getTin(),propertyOwner.getUsername(),propertyOwner.getEmail(),propertyOwner.getName(),propertyOwner.getSurname(),propertyOwner.getAddress(),propertyOwner.getPhoneNumber(),true);

        when(propertyOwnerRepository.findPropertyIdsByUserId(propertyOwner.getId())).thenReturn(Collections.emptyList());
        when(ownerMapper.userToUserSearchResponseDto(propertyOwner)).thenReturn(userDto);
        List<String> properties = List.of("prop1", "prop2");

        when(propertyOwnerRepository.findPropertyIdsByUserId(propertyOwner.getId())).thenReturn(properties);
        when(ownerMapper.userToUserSearchResponseDto(propertyOwner)).thenReturn(userDto);


        UserDetailsDto userDetails = propertyOwnerService.userDetails(propertyOwner);

        assertEquals(userDto, userDetails.userInfo());
        assertEquals(properties, userDetails.properties());
    }

    /**
     * Test the userDetails method when a property owner has no properties.
     * <p>
     * This test verifies that the userDetails method returns the expected UserDetails object
     * with the correct user details and an empty list of properties when a property owner does not exist.
     */
    @Test
    void userDetails_ReturnsUserDetailsWithEmptyProperties_WhenPropertyOwnerDoesNotExist() {

        UserSearchResponseDto userDto = new UserSearchResponseDto(propertyOwner.getId(),propertyOwner.getTin(),propertyOwner.getUsername(),propertyOwner.getEmail(),propertyOwner.getName(),propertyOwner.getSurname(),propertyOwner.getAddress(),propertyOwner.getPhoneNumber(),true);

        when(propertyOwnerRepository.findPropertyIdsByUserId(propertyOwner.getId())).thenReturn(Collections.emptyList());
        when(ownerMapper.userToUserSearchResponseDto(propertyOwner)).thenReturn(userDto);


        UserDetailsDto userDetails = propertyOwnerService.userDetails(propertyOwner);


        assertEquals(userDto, userDetails.userInfo());
        assertEquals(Collections.emptyList(), userDetails.properties());
    }

    /**
     * Test the findUser method when a user with the provided TIN exists and is active.
     *
     * This test verifies that the findUser method returns the expected PropertyOwner object
     * when a user with the provided TIN exists and is active.
     */
    @Test
    void findUser_ReturnsActiveUser_WhenUserExistsAndIsActive() {
        // Arrange
        String tin = propertyOwner.getTin();
;

        when(propertyOwnerRepository.findByTin(tin)).thenReturn(Optional.of(propertyOwner));


        PropertyOwner actualUser = propertyOwnerService.findUser(tin);


        assertEquals(propertyOwner, actualUser);
    }

    /**
     * Test the findUser method when a user with the provided TIN does not exist.
     *
     * This test verifies that the findUser method throws an EntityNotFoundException
     * when a user with the provided TIN does not exist.
     */
    @Test
    void findUser_ThrowsEntityNotFoundException_WhenUserDoesNotExist() {

        String tin = propertyOwner.getTin();

        when(propertyOwnerRepository.findByTin(tin)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> propertyOwnerService.findUser(tin));
    }

    /**
     * Test the findUser method when a user with the provided TIN exists but is not active.
     *
     * This test verifies that the findUser method throws an EntityNotFoundException
     * when a user with the provided TIN exists but is not active.
     */
    @Test
    void findUser_ThrowsEntityNotFoundException_WhenUserExistsButIsNotActive() {

        String tin = propertyOwner.getTin();

        propertyOwner.setActive(false);

        when(propertyOwnerRepository.findByTin(tin)).thenReturn(Optional.of(propertyOwner));


        assertThrows(EntityNotFoundException.class, () -> propertyOwnerService.findUser(tin));
    }

}



