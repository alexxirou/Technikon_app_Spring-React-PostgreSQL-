package com.scytalys.technikon.controller;

import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.service.PropertyOwnerService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;


@RestController

@AllArgsConstructor
@RequestMapping("/users/propertyOwners")
public class PropertyOwnerController {


    @Autowired
    private final PropertyOwnerService propertyOwnerService;


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataException(Exception e) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Error-Message", e.getMessage());
        return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);

    }




    @PostMapping("/subscribe")
    public ResponseEntity<EntityModel<UserResponseDto>> createPropertyOwner(@RequestBody PropertyOwner newUser) {
        newUser.setEmail(newUser.getEmail().toLowerCase());
        newUser.setUsername(newUser.getUsername().toLowerCase());
        propertyOwnerService.verifyConstraintsId(newUser.getId());
        propertyOwnerService.verifyConstraintsEmail(newUser.getEmail());
        propertyOwnerService.verifyConstraintsUsername(newUser.getUsername());
        PropertyOwner createdUser = (PropertyOwner) propertyOwnerService.createDBUser(newUser);
        UserResponseDto userinfo = propertyOwnerService.createUserResponseDto(createdUser.getId(), createdUser.getUsername(), createdUser.getEmail(), createdUser.getVersion());

        // Build URI for the created user resource
        URI userLocation = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/propertyOwners/{id}").buildAndExpand(createdUser.getId()).toUri();

        // Build URI for the created property resource
         URI propertyAddLocation = ServletUriComponentsBuilder.fromCurrentContextPath().path("/properties/add").build().toUri();


        // Return ResponseEntity with the created user info, URI of the created user resource, and URI of the created property resource
        EntityModel<UserResponseDto> userModel = EntityModel.of(userinfo);
        userModel.add(Link.of(userLocation.toString(), "manage user "));
        userModel.add(Link.of(propertyAddLocation.toString(), "add property"));

        return ResponseEntity.created(userLocation).body(userModel);
    }



    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDto>> findUserById(@PathVariable long id) {
        User user = propertyOwnerService.searchUserById(id);
        propertyOwnerService.verifySearchResult(user);

        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getVersion());
        EntityModel<UserResponseDto> userModel = EntityModel.of(userinfo);
     URI propertyAddLocation = ServletUriComponentsBuilder.fromCurrentContextPath().path("/properties/add").build().toUri();
        URI userModifyEmailLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/email/update")
                .queryParam("id", userinfo.id())
                .queryParam("email", userinfo.email())
                .queryParam("version", userinfo.version())
                .build()
                .toUri();



       URI userDeleteLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/unsubscribe").build().toUri();
       URI propertiesLocation = ServletUriComponentsBuilder.fromCurrentContextPath().path("/properties").build().toUri();
        userModel.add(Link.of(propertiesLocation.toString(), "properties"));
        userModel.add(Link.of(propertyAddLocation.toString(), "add property"));
        userModel.add(Link.of(userModifyEmailLocation .toString(), "modify email "));
        userModel.add(Link.of(userDeleteLocation .toString(), "unsubscribe"));
        return ResponseEntity.ok(userModel);
    }

    @GetMapping("/{username}")
    public ResponseEntity<EntityModel<UserResponseDto>> findUserByUsername(@PathVariable String username) {
        username=username.toLowerCase();
        User user = propertyOwnerService.searchUserByUsername(username);
        propertyOwnerService.verifySearchResult(user);

        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getVersion());
        EntityModel<UserResponseDto> userModel = EntityModel.of(userinfo);
        URI propertyAddLocation = ServletUriComponentsBuilder.fromCurrentContextPath().path("/properties/add").build().toUri();
        URI userModifyEmailLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/email/update")
                .queryParam("id", userinfo.id())
                .queryParam("email", userinfo.email())
                .queryParam("version", userinfo.version())
                .build()
                .toUri();

        URI userDeleteLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/unsubscribe").build().toUri();
        URI propertiesLocation = ServletUriComponentsBuilder.fromCurrentContextPath().path("/properties").build().toUri();
        userModel.add(Link.of(propertiesLocation.toString(), "properties"));
        userModel.add(Link.of(propertiesLocation.toString(), "properties"));
        userModel.add(Link.of(propertyAddLocation.toString(), "add property"));
        userModel.add(Link.of(userModifyEmailLocation .toString(), "modify email "));

        userModel.add(Link.of(userDeleteLocation .toString(), "unsubscribe"));
        return ResponseEntity.ok(userModel);
    }

    @GetMapping("/{email}")
    public ResponseEntity<EntityModel<UserResponseDto>> findUserByEmail(@PathVariable String email) {
        email=email.toLowerCase();
        User user = propertyOwnerService.searchUserByEmail(email);
        propertyOwnerService.verifySearchResult(user);

        UserResponseDto userinfo= propertyOwnerService.createUserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getVersion());
        EntityModel<UserResponseDto> userModel = EntityModel.of(userinfo);
        URI propertyAddLocation = ServletUriComponentsBuilder.fromCurrentContextPath().path("/properties/add").build().toUri();
        URI userModifyEmailLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/email/update")
                .queryParam("id", userinfo.id())
                .queryParam("email", userinfo.email())
                .queryParam("version", userinfo.version())
                .build()
                .toUri();
        URI userDeleteLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/unsubscribe").build().toUri();
        URI propertiesLocation = ServletUriComponentsBuilder.fromCurrentContextPath().path("/properties").build().toUri();
        userModel.add(Link.of(propertiesLocation.toString(), "properties"));
        userModel.add(Link.of(propertiesLocation.toString(), "properties"));
        userModel.add(Link.of(propertyAddLocation.toString(), "add property"));
        userModel.add(Link.of(userModifyEmailLocation .toString(), "modify email "));
        userModel.add(Link.of(userDeleteLocation .toString(), "unsubscribe"));
        return ResponseEntity.ok(userModel);
    }





    @PutMapping("/{id}/update")
    public ResponseEntity<String> updateUser(@PathVariable long id, @RequestParam(required = false) String address, @RequestParam(required = false) String password, @RequestParam(required = false) String email, @RequestParam long version) {
        int res =0;
        if (address != null) {

            res =  propertyOwnerService.updateUserAddress(id, address, version);
        } else if (password != null) {
            // Update password
            // Your implementation here
            res =  propertyOwnerService.updateUserPassword(id, password, version);
        } else if (email != null) {
            res =  propertyOwnerService.updateUserPassword(id, email, version);
        } else {
            // Handle invalid request
            throw new IllegalArgumentException("Update field not valid.");
        }



        if (res == 0) {
            User checkUserExists=propertyOwnerService.searchUserById(id);// Fetch the updated user by ID
            propertyOwnerService.verifySearchResult(checkUserExists);
            throw new DataAccessResourceFailureException("Failed to update address for user with id: "+id);
        }

        // Build URI for managing the updated user
        URI manageUserLocation = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/propertyOwners/{id}").buildAndExpand(id).toUri();

        // Return ResponseEntity with the links to manage user and add property
        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User Updated.");
        headers.setLocation(manageUserLocation); // Set Location header to manage user
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }



    @DeleteMapping("/unsubscribe")
    public ResponseEntity<String> deleteUser(@RequestParam long id, @RequestParam long version) {
        ArrayList<Long> properties = propertyOwnerService.findPropertiesForUser(id);
        if(propertyOwnerService.checkUserHasProperties(properties)){
            propertyOwnerService.softDeleteUser(id,version);
        }
        else{propertyOwnerService.deleteUser(id);}
        User user = propertyOwnerService.searchUserById(id);
        if(user!=null && user.isActive()) throw new DataAccessResourceFailureException("Failed to delete user with id: "+id);

        URI createUserLocation = ServletUriComponentsBuilder.fromCurrentContextPath().path("/subscribe").buildAndExpand(id).toUri();

        HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User deleted.");
        headers.setLocation(createUserLocation); // Set Location header to manage user

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

}
