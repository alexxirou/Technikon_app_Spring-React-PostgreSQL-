package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.PropertyOwner;

import com.scytalys.technikon.repository.PropertyOwnerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;


import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    public void testCreateUser() {
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        PropertyOwner result =  propertyOwnerService.createUser(propertyOwner);
        System.out.println(result);
        assertEquals(propertyOwner, result);

    }

    @Test
    public void testSearchUserById(){
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        when(propertyOwnerRepository.findById(any(Long.class))).thenReturn(Optional.of(propertyOwner));
        propertyOwnerService.createUser(propertyOwner);
        PropertyOwner result = propertyOwnerService.searchUserById(propertyOwner.getId());
        assertEquals(propertyOwner, result);
    }
    @Test
    public void testSearchUserByIdFail(){
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        when(propertyOwnerRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        propertyOwnerService.createUser(propertyOwner);
        assertThrows(NoSuchElementException.class, () -> propertyOwnerService.searchUserById(0L));
    }

    @Test
    public void testSearchUserByUsername(){
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        when(propertyOwnerRepository.findByUsername(any(String.class))).thenReturn(Optional.of(propertyOwner));
        propertyOwnerService.createUser(propertyOwner);
        PropertyOwner result = propertyOwnerService.searchUserByUsername(propertyOwner.getUsername());
        assertEquals(propertyOwner, result);
    }
    @Test
    public void testSearchUserByUsernameFail(){
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        when(propertyOwnerRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        propertyOwnerService.createUser(propertyOwner);
        assertThrows(NoSuchElementException.class, () -> propertyOwnerService.searchUserByUsername(""));
    }

    @Test
    public void testSearchUserByEmail(){
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        when(propertyOwnerRepository.findByEmail(any(String.class))).thenReturn(Optional.of(propertyOwner));
        propertyOwnerService.createUser(propertyOwner);
        PropertyOwner result = propertyOwnerService.searchUserByEmail(propertyOwner.getEmail());
        assertEquals(propertyOwner, result);
    }

    @Test
    public void testSearchUserByEmailFail(){
        when(propertyOwnerRepository.save(any(PropertyOwner.class))).thenReturn(propertyOwner);
        when(propertyOwnerRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        propertyOwnerService.createUser(propertyOwner);
        assertThrows(NoSuchElementException.class, () -> propertyOwnerService.searchUserByEmail(""));
    }

}