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
    @PutMapping("/{tin}")
    public ResponseEntity<String> updateUser(@PathVariable String tin, @RequestBody UserUpdateDto updateRequest, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            //long id = userInfoDetails.getId();
            String authTin = userInfoDetails.getTin();
            if (!authTin.equals(tin)) throw new AccessDeniedException("You are not authorized to update another user.");
        }
        propertyOwnerService.updateUser(tin, updateRequest);
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "User updated.");
        return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
    }


    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{tin}")
    public ResponseEntity<String> deleteUser(@PathVariable String tin, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            String authTin = userInfoDetails.getTin();
            if (!authTin.equals(tin)) throw new AccessDeniedException("You are not authorized to delete another user.");
        }
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
