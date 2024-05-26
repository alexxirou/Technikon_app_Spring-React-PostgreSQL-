package com.scytalys.technikon.controller;

import com.scytalys.technikon.dto.repair.*;
import com.scytalys.technikon.security.service.UserInfoDetails;
import com.scytalys.technikon.service.PropertyRepairService;
import com.scytalys.technikon.utility.HeaderUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("api/property-repairs")
@AllArgsConstructor
public class PropertyRepairController {
    private final PropertyRepairService propertyRepairService;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<PropertyRepairDto> create(@RequestBody PropertyRepairDto propertyRepairDto, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            long id = userInfoDetails.getId();
            if (id != propertyRepairDto.propertyOwnerId()) throw new BadCredentialsException("You are not authorized to create property repair for this owner.");
        }
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Property repair created successfully.");
        return new ResponseEntity<>(propertyRepairService.createPropertyRepair(propertyRepairDto),headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{propertyOwnerId}/{repairId}")
    public ResponseEntity<PropertyRepairDto> readOne(@PathVariable long repairId, @PathVariable long propertyOwnerId, Authentication authentication){
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            long id = userInfoDetails.getId();
            if (id != propertyOwnerId) throw new BadCredentialsException("You are not authorized to search property repair for this owner.");
        }
        PropertyRepairDto propertyRepairDto = propertyRepairService.getPropertyRepair(repairId);
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Property repair found.");
        return new ResponseEntity<>(propertyRepairDto, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<PropertyRepairDto>> readAll(){
        List<PropertyRepairDto> propertyRepairDtos = propertyRepairService.getAllPropertyRepairs();
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Property repairs found.");
        return new ResponseEntity<>(propertyRepairDtos, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/all-by-owner/{propertyOwnerId}")
    public ResponseEntity<List<PropertyRepairDto>> readAllByOwner(@PathVariable long propertyOwnerId, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            long id = userInfoDetails.getId();
            if (id != propertyOwnerId) throw new BadCredentialsException("You are not authorized to search property repairs for this owner.");
        }
        List<PropertyRepairDto> propertyRepairDtos = propertyRepairService.getPropertyRepairsByOwner(propertyOwnerId);
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Property repairs found.");
        return new ResponseEntity<>(propertyRepairDtos,headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{propertyOwnerId}/get-by-date/{date}")
    public ResponseEntity<List<PropertyRepairDto>> searchPropertyRepairsByDate(@PathVariable long propertyOwnerId, @PathVariable LocalDate date, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            long id = userInfoDetails.getId();
            if (id != propertyOwnerId) throw new BadCredentialsException("You are not authorized to search property repairs for this owner.");
        }
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Property repairs found.");
        return new ResponseEntity<>(propertyRepairService.searchPropertyRepairsByDate(propertyOwnerId, date), headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{propertyOwnerId}/get-by-dates/{firstDate}/{lastDate}")
    public ResponseEntity<List<PropertyRepairDto>> searchPropertyRepairsByDates(@PathVariable long propertyOwnerId, @PathVariable LocalDate firstDate, @PathVariable LocalDate lastDate, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            long id = userInfoDetails.getId();
            if (id != propertyOwnerId) throw new BadCredentialsException("You are not authorized to search property repairs for this owner.");
        }
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Property repairs found.");
        return new ResponseEntity<>(propertyRepairService.searchPropertyRepairsByDates(propertyOwnerId, firstDate, lastDate), headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{propertyOwnerId}/{propertyRepairId}")
    public ResponseEntity<PropertyRepairUpdateDto> update(@PathVariable long propertyRepairId, @PathVariable long propertyOwnerId, @RequestBody PropertyRepairUpdateDto dto, Authentication authentication){
        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
        if (!role.equals("ROLE_ADMIN"))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            long id = userInfoDetails.getId();
            if (id != propertyOwnerId) throw new BadCredentialsException("You are not authorized to update a property repair for this owner.");
        }
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Property repair updated.");
        return new ResponseEntity<>(propertyRepairService.updatePropertyRepair(propertyRepairId, dto, propertyOwnerId, role), headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{propertyOwnerId}/delete/{propertyRepairId}")
    public ResponseEntity<Object> deletePropertyRepair(@PathVariable long propertyRepairId,@PathVariable long propertyOwnerId, Authentication authentication) {
        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
        if (!role.equals("ROLE_ADMIN"))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            long id = userInfoDetails.getId();
            if (id != propertyOwnerId) throw new BadCredentialsException("You are not authorized to delete property repair for this owner.");
        }
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Property repair deleted.");
        propertyRepairService.deletePropertyRepair(propertyOwnerId,propertyRepairId,role);
        return new ResponseEntity<>(headers,HttpStatus.OK);
    }
}
