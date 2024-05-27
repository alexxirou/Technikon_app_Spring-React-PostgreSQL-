package com.scytalys.technikon.controller;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.dto.user.UserDetailsDto;
import com.scytalys.technikon.dto.user.UserSearchDto;
import com.scytalys.technikon.dto.user.UserSearchResponseDto;
import com.scytalys.technikon.dto.user.UserUpdateDto;
import com.scytalys.technikon.security.service.UserInfoDetails;
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
@RequestMapping("/api/propertyOwners")
public class PropertyOwnerController {
    private final PropertyOwnerService propertyOwnerService;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<UserSearchResponseDto>>  findUsers(
            @RequestParam(required = false) String tin,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email, Authentication authentication
    ) {
        UserSearchDto searchRequest = new UserSearchDto(tin, username, email);
        List<PropertyOwner> users = propertyOwnerService.searchUser(searchRequest);
        List<UserSearchResponseDto> userInfo =propertyOwnerService.createSearchResponse(users);
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Users found.");
        return new ResponseEntity<>(userInfo, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{tin}")
    public ResponseEntity<UserDetailsDto> showUser(@PathVariable String tin, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            //long id = userInfoDetails.getId();
            String authTin = userInfoDetails.getTin();
            if (!authTin.equals(tin)) throw new AccessDeniedException("You are not authorized to update another user.");
        }
        UserDetailsDto userInfo=propertyOwnerService.userDetails(propertyOwnerService.findUser(tin));
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "User with tin found.");
        return new ResponseEntity<>(userInfo, headers, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/id/{userId}")
    public ResponseEntity<UserDetailsDto> showUser(@PathVariable long userId, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            long id = userInfoDetails.getId();

            if( userId!=id) throw new AccessDeniedException("You are not authorized to update another user.");
        }
        UserDetailsDto userInfo=propertyOwnerService.userDetails(propertyOwnerService.findUser(userId));
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "User with tin found.");
        return new ResponseEntity<>(userInfo, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable long id, @RequestBody UserUpdateDto updateRequest, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();

            long authId = userInfoDetails.getId();
            if (authId!=id) throw new AccessDeniedException("You are not authorized to update another user.");
        }
        propertyOwnerService.updateUser(id, updateRequest);
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "User updated.");
        return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
    }


    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            long authId = userInfoDetails.getId();
            if (authId!=id) throw new AccessDeniedException("You are not authorized to delete another user.");
        }
        HttpHeaders headers;
        if(propertyOwnerService.checkUserHasProperties(id)){
            propertyOwnerService.softDeleteUser(id);
            headers= HeaderUtility.createHeaders("Success-Header", "User deactivated.");
        }
        else {
            propertyOwnerService.deleteUser(id);
            headers = HeaderUtility.createHeaders("Success-Header", "User deleted.");
        }
        return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
    }

}
