package com.scytalys.technikon.controller;

import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.service.PropertyOwnerService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        newUser.setEmail(newUser.getEmail().toLowerCase());
        newUser.setUsername(newUser.getUsername().toLowerCase());
        propertyOwnerService.verifyConstraintsId(newUser.getId());
        propertyOwnerService.verifyConstraintsEmail(newUser.getEmail());
        propertyOwnerService.verifyConstraintsUsername(newUser.getUsername());
        PropertyOwner createdUser = propertyOwnerService.createUser(newUser);
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User registered successfully");
        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(createdUser.getId(), createdUser.getUsername(), createdUser.getEmail(), createdUser.getVersion());
        return new ResponseEntity<>(userinfo, headers, HttpStatus.CREATED);
    }

    @GetMapping("users/searchId")
    public ResponseEntity<UserResponseDto> findUserById(@RequestParam Long id) {
        User user = propertyOwnerService.searchUserById(id);
        propertyOwnerService.verifySearchResult(user);
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User found.");
        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getVersion());
        return new ResponseEntity<>(userinfo, headers, HttpStatus.OK);
    }

    @GetMapping("users/searchUsername")
    public ResponseEntity<UserResponseDto> findUserByUsername(@RequestParam String username) {
        username=username.toLowerCase();
        User user = propertyOwnerService.searchUserByUsername(username);
        propertyOwnerService.verifySearchResult(user);
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User found.");
        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getVersion());
        return new ResponseEntity<>(userinfo, headers, HttpStatus.OK);
    }

    @GetMapping("users/searchEmail")
    public ResponseEntity<UserResponseDto> findUserByEmail(@RequestParam String email) {
        email=email.toLowerCase();
        User user = propertyOwnerService.searchUserByEmail(email);
        propertyOwnerService.verifySearchResult(user);
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User found.");
        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getVersion());
        return new ResponseEntity<>(userinfo, headers, HttpStatus.OK);
    }

    @PutMapping("users/UpdateEmail")
    public ResponseEntity<UserResponseDto> updateUserEmail(@RequestBody String email, @RequestBody long id, @RequestBody long version) {
        email=email.toLowerCase();
        PropertyOwner user =  propertyOwnerService.updateUserAddress(id, email, version);
        if (user == null) { //update failed
            User checkUserExists = propertyOwnerService.searchUserById(id);// Fetch the updated user by ID
            propertyOwnerService.verifySearchResult(checkUserExists); //check if user with id exists
            propertyOwnerService.verifyConstraintsEmail(email); //check if email is already in the DB;
            throw new DataAccessResourceFailureException("Failed to to update email for user with id: " + id);
        }
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User Updated.");
        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getVersion());
        return new ResponseEntity<>(userinfo, headers, HttpStatus.OK);
    }


    @PutMapping("users/UpdateAddress")
    public ResponseEntity<UserResponseDto> updateUserAddress(@RequestBody String address, @RequestBody long id, @RequestBody long version) {

        PropertyOwner user =  propertyOwnerService.updateUserAddress(id, address, version);
        if (user == null) {
            User checkUserExists=propertyOwnerService.searchUserById(id);// Fetch the updated user by ID
            propertyOwnerService.verifySearchResult(checkUserExists);
            throw new DataAccessResourceFailureException("Failed to to update address for user with id: "+id+ "please try again.");
        }
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User Updated.");
        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getVersion());
        return new ResponseEntity<>(userinfo, headers, HttpStatus.OK);
    }

    @PutMapping("users/UpdatePassword")
    public ResponseEntity<UserResponseDto> updateUserPassword(@RequestBody String password, @RequestBody long id, @RequestBody long version) {

        User user = propertyOwnerService.updateUserEmail(id, password, version);
        if (user == null) {
            User checkUserExists=propertyOwnerService.searchUserById(id);// Fetch the updated user by ID
            propertyOwnerService.verifySearchResult(checkUserExists);
            throw new DataAccessResourceFailureException("Failed to to update password for user with id: "+id+ "please try again.");
        }

        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User Updated.");
        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getVersion());
        return new ResponseEntity<>(userinfo, headers, HttpStatus.OK);
    }

    @PostMapping("users/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam long id, long version) {
        propertyOwnerService.deleteUser(id);
        User user = propertyOwnerService.searchUserById(id);
        if(user!=null && user.isActive()) throw new DataAccessResourceFailureException("Failed to delete user with id: "+id);
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User Deleted.");
        return new ResponseEntity<>( headers, HttpStatus.OK);
    }

    }
