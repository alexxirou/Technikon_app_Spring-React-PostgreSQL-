package com.scytalys.technikon.controller;

import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.service.PropertyOwnerService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
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
    public ResponseEntity<String> handleDataException(Exception e) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Error-Message", e.getMessage());
        return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createPropertyOwner(@RequestBody PropertyOwner newUser) {
        propertyOwnerService.verifyConstraintsId(newUser.getId());
        propertyOwnerService.verifyConstraintsEmail(newUser.getEmail().toLowerCase());
        propertyOwnerService.verifyConstraintsUsername(newUser.getUsername().toLowerCase());
        PropertyOwner createdUser = propertyOwnerService.createUser(newUser);
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User registered successfully");
        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(createdUser.getId(), createdUser.getUsername(), createdUser.getEmail());
        return new ResponseEntity<>(userinfo, headers, HttpStatus.CREATED);
    }

    @GetMapping("users/searchId")
    public ResponseEntity<UserResponseDto> findUserById(@RequestParam Long id) {
        User user = propertyOwnerService.searchUserById(id);
        propertyOwnerService.verifySearchResult(user);
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User found.");
        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(user.getId(), user.getUsername(), user.getEmail());
        return new ResponseEntity<>(userinfo, headers, HttpStatus.OK);
    }

    @GetMapping("users/searchUsername")
    public ResponseEntity<UserResponseDto> findUserById(@RequestParam String username) {
        User user = propertyOwnerService.searchUserByUsername(username.toLowerCase());
        propertyOwnerService.verifySearchResult(user);
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User found.");
        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(user.getId(), user.getUsername(), user.getEmail());
        return new ResponseEntity<>(userinfo, headers, HttpStatus.OK);
    }

    @GetMapping("users/searchEmail")
    public ResponseEntity<UserResponseDto> findUserByEmail(@RequestParam String email) {
        User user = propertyOwnerService.searchUserByEmail(email.toLowerCase());
        propertyOwnerService.verifySearchResult(user);
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User found.");
        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(user.getId(), user.getUsername(), user.getEmail());
        return new ResponseEntity<>(userinfo, headers, HttpStatus.OK);
    }

    @PutMapping("users/UpdateEmail")
    public ResponseEntity<UserResponseDto> updateUserEmail(@RequestParam String email, @RequestParam long id) {
        email=email.toLowerCase();
        User user = propertyOwnerService.searchUserById(id);
        propertyOwnerService.verifySearchResult(user);
        propertyOwnerService.verifyConstraintsEmail(email);
        propertyOwnerService.updateUserEmail(email, user);
        user = propertyOwnerService.searchUserById(id); // Fetch the updated user by ID
        if (!email.equals(user.getEmail())) throw new DataAccessResourceFailureException("Failed to to update email for user with id: "+id);
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User Updated.");
        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(user.getId(), user.getUsername(), user.getEmail());
        return new ResponseEntity<>(userinfo, headers, HttpStatus.OK);
    }


    @PutMapping("users/UpdateAddress")
    public ResponseEntity<UserResponseDto> updateUserAddress(@RequestParam String address, @RequestParam long id) {
        PropertyOwner user = (PropertyOwner) propertyOwnerService.searchUserById(id);
        propertyOwnerService.verifySearchResult(user);
        propertyOwnerService.updateUserAddress(address, user);
        user = (PropertyOwner) propertyOwnerService.searchUserById(id); // Fetch the updated user by ID
        if (!address.equals(user.getAddress())) throw new DataAccessResourceFailureException("Failed to to update address for user with id: "+id);
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User Updated.");
        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(user.getId(), user.getUsername(), user.getEmail());
        return new ResponseEntity<>(userinfo, headers, HttpStatus.OK);
    }

    @PutMapping("users/UpdatePassword")
    public ResponseEntity<UserResponseDto> updateUserPassword(@RequestParam String password, @RequestParam long id) {
        User user = propertyOwnerService.searchUserById(id);
        propertyOwnerService.verifySearchResult(user);
        propertyOwnerService.updateUserEmail(password, user);
        user = propertyOwnerService.searchUserById(id); // Fetch the updated user by ID
        if (!password.equals(user.getPassword())) throw new DataAccessResourceFailureException("Failed to to update password for user with id: "+id);
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User Updated.");
        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(user.getId(), user.getUsername(), user.getEmail());
        return new ResponseEntity<>(userinfo, headers, HttpStatus.OK);
    }

    @PostMapping("users/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam long id) {
        propertyOwnerService.deleteUser(id);
        User user = propertyOwnerService.searchUserById(id);
        if(user!=null && user.isActive()) throw new DataAccessResourceFailureException("Failed to delete user with id: "+id);
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User Deleted.");
        return new ResponseEntity<>( headers, HttpStatus.OK);
    }

    }
