package com.scytalys.technikon.controller;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.dto.*;
import com.scytalys.technikon.security.dto.AuthRequest;
import com.scytalys.technikon.service.PropertyOwnerService;
import com.scytalys.technikon.utility.HeaderUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController

@AllArgsConstructor
@RequestMapping("/api/v2/users/propertyOwners")
public class PropertyOwnerController {

    private final PropertyOwnerService propertyOwnerService;




    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{tin}")
    public ResponseEntity<UserDetailsDto> showUser(@PathVariable String tin, Authentication authentication) {
        UserDetailsDto userInfo=propertyOwnerService.userDetails(propertyOwnerService.findUser(tin), authentication);
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "User with tin found.");
        return new ResponseEntity<>(userInfo, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{tin}")
    public ResponseEntity<String> updateUser(@PathVariable String tin, @RequestBody UserUpdateDto updateRequest, Authentication authentication) {

        propertyOwnerService.updateUser(tin, updateRequest, authentication);


        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "User updated.");
        return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{tin}{version}")
    public ResponseEntity<String> deleteUser(@PathVariable String tin, @RequestParam long version, Authentication authentication) {
        HttpHeaders headers;
        if(propertyOwnerService.checkUserHasProperties(tin)){
            propertyOwnerService.softDeleteUser(tin ,authentication);
            headers= HeaderUtility.createHeaders("Success-Header", "User deactivated.");
        }
        else {
            propertyOwnerService.deleteUser(tin, authentication);
            headers = HeaderUtility.createHeaders("Success-Header", "User deleted.");
        }
        return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
    }

}
