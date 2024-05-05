package com.scytalys.technikon.controller;

import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.service.PropertyOwnerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@AllArgsConstructor
public class PropertyOwnerController {

    @Autowired
    private final PropertyOwnerService propertyOwnerService;


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<UserResponseDto> handleDataException(Exception e) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Error-Message", e.getMessage());

        return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createPropertyOwner(@RequestBody PropertyOwner newUser) {
        try{
        propertyOwnerService.verifyConstraintsId(newUser.getId());
        propertyOwnerService.verifyConstraintsEmail(newUser.getEmail());
        propertyOwnerService.verifyConstraintsUsername(newUser.getUsername());
        PropertyOwner createdUser = propertyOwnerService.createUser(newUser);
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User registered successfully");
        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(createdUser.getId(), createdUser.getUsername(), createdUser.getEmail());
        return new ResponseEntity<>(userinfo, headers, HttpStatus.OK);
        }catch (DataIntegrityViolationException e){
            return handleDataException(e);
        }
    }

}
