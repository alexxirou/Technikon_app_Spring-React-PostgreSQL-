package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.dto.UserCreationDto;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.security.service.JwtService;
import com.scytalys.technikon.security.service.JwtServiceImpl;
import com.scytalys.technikon.security.service.UserInfoDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Key;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtServiceTest {

    @Autowired
    private JwtServiceImpl jwtService;
    @Mock
    private PropertyOwnerRepository propertyOwnerRepository;

    private PropertyOwner propertyOwner;

    @BeforeEach
    public void setup(){

        MockitoAnnotations.openMocks(this);

        propertyOwner= new PropertyOwner();
        propertyOwner.setId(0L);// id
        propertyOwner.setTin("1651614865GR");
        propertyOwner.setName("Johny"); // name
        propertyOwner.setSurname("Doep"); // surname
        propertyOwner.setEmail("jdee@hotmail.com"); // email
        propertyOwner.setUsername("jdeeefe"); // username
        propertyOwner.setPassword("pass"); // password
        propertyOwner.setAddress("somewhere"); // address
        propertyOwner.setPhoneNumber("+30999582486");// phoneNumber
        propertyOwner.setVersion(0);
        //dto =new UserCreationDto(propertyOwner.getTin(), propertyOwner.getName(), propertyOwner.getSurname(), propertyOwner.getEmail(), propertyOwner.getUsername(), propertyOwner.getPassword(), propertyOwner.getAddress(), propertyOwner.getPhoneNumber());


    }



    @Test
    public void testGenerateTokenAndVerify() {

        UserInfoDetails userInfoDetails= new UserInfoDetails(propertyOwner);
        // Generate token
        String token= jwtService.generateToken(userInfoDetails);



        // Extract claims
        String extractedTin = jwtService.extractTin(token);
        long extractedId = jwtService.extractId(token);
        String extractedUsername = jwtService.extractUsername(token);
        Date expirationDate = jwtService.extractExpiration(token);

        // Validate token
        UserDetails userDetails = new UserInfoDetails(propertyOwner); // Mock UserDetails
        boolean isValid = jwtService.validateToken(token, userDetails);

        // Assertions
        assertEquals(propertyOwner.getTin(), extractedTin);
        assertEquals(propertyOwner.getId(), extractedId);
        assertEquals(propertyOwner.getUsername(), extractedUsername);
        assertTrue(expirationDate.after(new Date())); // Ensure expiration date is in the future
        assertTrue(isValid);
    }
}
