package com.scytalys.technikon.controller;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.dto.*;
import com.scytalys.technikon.service.PropertyOwnerService;
import com.scytalys.technikon.utility.HeaderUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController

@AllArgsConstructor
@RequestMapping("/api/v2/users/propertyOwners")
public class PropertyOwnerController {

    private final PropertyOwnerService propertyOwnerService;





    @GetMapping("/")
    public ResponseEntity<List<UserSearchResponseDto>>  findUsers(
            @RequestParam(required = false) String tin,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email
    ) {
        UserSearchDto searchRequest = new UserSearchDto(tin, username, email);
        List<PropertyOwner> users = propertyOwnerService.searchUser(searchRequest);
        List<UserSearchResponseDto> userInfo =propertyOwnerService.createSearchResponse(users);
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Users found.");
        return new ResponseEntity<>(userInfo, headers, HttpStatus.OK);
    }


    @GetMapping("/{tin}")
    public ResponseEntity<UserDetailsDto> showUser(@RequestParam String tin) {
        UserDetailsDto userInfo=propertyOwnerService.userDetails(propertyOwnerService.findUser(tin));
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "User with tin found.");
        return new ResponseEntity<>(userInfo, headers, HttpStatus.OK);
    }

    @PutMapping("/{tin}")
    public ResponseEntity<String> updateUser(@RequestParam String tin, @RequestBody UserUpdateDto updateRequest) {
        propertyOwnerService.updateUser(tin, updateRequest);
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "User updated.");
        return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
    }



    @DeleteMapping("/{tin}{version}")
    public ResponseEntity<String> deleteUser(@RequestParam String tin, @RequestParam long version) {
        HttpHeaders headers;
        if(propertyOwnerService.checkUserHasProperties(tin)){
            propertyOwnerService.softDeleteUser(tin);
            headers= HeaderUtility.createHeaders("Success-Header", "User deactivated.");
        }
        else {
            propertyOwnerService.deleteUser(tin);
            headers = HeaderUtility.createHeaders("Success-Header", "User deleted.");
        }
        return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
    }

}
