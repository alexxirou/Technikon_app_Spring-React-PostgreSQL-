package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.PropertyOwner;

import com.scytalys.technikon.repository.PropertyOwnerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;


import java.sql.SQLIntegrityConstraintViolationException;

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

        PropertyOwner result = (PropertyOwner) propertyOwnerService.createUser(propertyOwner);
        System.out.println(result);

        assertEquals(propertyOwner, result);

    }

}