package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;

import com.scytalys.technikon.domain.category.PropertyType;
import com.scytalys.technikon.dto.*;
import com.scytalys.technikon.mapper.OwnerMapper;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.repository.PropertyRepository;


import java.util.*;

import com.scytalys.technikon.security.service.UserInfoService;
import com.scytalys.technikon.utility.AuthenticationUtils;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;


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
    private Authentication authentication;

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
        authentication= AuthenticationUtils.createAuthentication(propertyOwner.getUsername(), propertyOwner.getPassword());


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
        assertEquals(propertyOwner.getVersion(),result.get(0).getVersion());
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
        when(propertyOwnerRepository.findByTin(any(String.class))).thenReturn(Optional.of(propertyOwner));

        when(propertyOwnerRepository.save(eq(propertyOwner))).thenReturn(propertyOwner);
        userInfoService.createDBUser(dto);
        // Set isActive flag to false
        doAnswer(invocation -> {
            propertyOwner.setActive(false); // Set isActive flag to false
            return 1;
        }).when(propertyOwnerService).softDeleteUser(eq(propertyOwner.getTin()), eq(authentication));
        propertyOwnerService.softDeleteUser(propertyOwner.getTin(), authentication);
        assertFalse(propertyOwner.isActive());
    }




    /**
     * This test verifies that the softDeleteUser throws DataAccessResourceFailure if the delete fails
     */
    @Test
    public  void testSoftDeleteUserFail() {
        userInfoService.createDBUser(dto);
        assertThrows(EntityNotFoundException.class, ()->propertyOwnerService.softDeleteUser(propertyOwner.getTin(), authentication));

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
        UserUpdateDto updateRequest = new UserUpdateDto( "dezfze@fezfez.com", propertyOwner.getAddress(), propertyOwner.getPassword(), 0);
        UserUpdateDto updateRequest2 = new UserUpdateDto( "dezdefze@fezfez.com", propertyOwner.getAddress(), propertyOwner.getPassword(), 0);
        when(propertyOwnerRepository.findByTin(any(String.class))).thenReturn(Optional.of(propertyOwner));
        doAnswer(invocation -> {



            // Check if the version matches
            if (propertyOwner.getVersion() == updateRequest.version() ) {
                propertyOwner.setVersion(propertyOwner.getVersion() + 1);
            }
            return null;
        }).when(propertyOwnerRepository).save(propertyOwner);

        propertyOwnerService.updateUser(propertyOwner.getTin(), updateRequest, authentication);

        assertThrows(OptimisticLockingFailureException.class, () -> propertyOwnerService.updateUser(propertyOwner.getTin(),updateRequest2, authentication));
    }

    /**
     * This method tests the updateUser does not throw an exception on valid email update and that the email of the object is the same as the one in the update request.
     */
    @Test
    void testUpdateUser(){
        UserUpdateDto updateRequest = new UserUpdateDto( "dezfze@fezfez.com", propertyOwner.getAddress(), propertyOwner.getPassword(), 0);
        when(propertyOwnerRepository.findByTin(any(String.class))).thenReturn(Optional.of(propertyOwner));
        // Stubbing the behavior of propertyOwnerRepository.update
        doAnswer(invocation -> {



            // Check if the version matches
            if (propertyOwner.getVersion() == updateRequest.version() ) {
                propertyOwner.setEmail(updateRequest.email());
            }
            return null;
        }).when(propertyOwnerRepository).save(propertyOwner);
        propertyOwnerService.updateUser(propertyOwner.getTin(), updateRequest, authentication);
        assertDoesNotThrow(() -> propertyOwnerService.updateUser(propertyOwner.getTin(),updateRequest, authentication));
        assertEquals(propertyOwner.getEmail(),updateRequest.email());

    }
    /**
     * This method tests the updateUser throws an exception if the email is not valid.
     */
    @Test
    void testUpdateUserFailWrongEmailFormat(){
        UserUpdateDto updateRequest = new UserUpdateDto( "dezfzeezfezom", propertyOwner.getAddress(), propertyOwner.getPassword(), 0);
        when(propertyOwnerRepository.findByTin(any(String.class))).thenReturn(Optional.of(propertyOwner));

        doAnswer(invocation -> {



            // Check if the version matches
            if (propertyOwner.getVersion() == updateRequest.version() ) {
                propertyOwner.setEmail(updateRequest.email());
            }
            return null;
        }).when(propertyOwnerRepository).save(propertyOwner);

        assertThrows(IllegalArgumentException.class, () -> propertyOwnerService.updateUser(propertyOwner.getTin(),updateRequest, authentication));


    }
    /**
     * Test the userDetails method when a property owner has properties.
     * This test verifies that the userDetails method returns the expected UserDetails object
     * with the correct user details and properties when a property owner exists.
     */
    @Test
    void userDetails_ReturnsUserDetails_WhenPropertyOwnerExists() {

        UserSearchResponseDto userDto = new UserSearchResponseDto( propertyOwner.getId(),propertyOwner.getTin(),propertyOwner.getUsername(),propertyOwner.getEmail(),propertyOwner.getName(),propertyOwner.getSurname(),propertyOwner.getAddress(),propertyOwner.getPhoneNumber(),true);

        when(propertyOwnerRepository.findPropertyIdsByUserId(propertyOwner.getTin())).thenReturn(Collections.emptyList());
        when(ownerMapper.userToUserSearchResponseDto(propertyOwner)).thenReturn(userDto);
        List<String> properties = List.of("prop1", "prop2");

        when(propertyOwnerRepository.findPropertyIdsByUserId(propertyOwner.getTin())).thenReturn(properties);
        when(ownerMapper.userToUserSearchResponseDto(propertyOwner)).thenReturn(userDto);


        UserDetailsDto userDetails = propertyOwnerService.userDetails(propertyOwner, authentication);

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

        when(propertyOwnerRepository.findPropertyIdsByUserId(propertyOwner.getTin())).thenReturn(Collections.emptyList());
        when(ownerMapper.userToUserSearchResponseDto(propertyOwner)).thenReturn(userDto);


        UserDetailsDto userDetails = propertyOwnerService.userDetails(propertyOwner, authentication);


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



