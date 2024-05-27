package com.scytalys.technikon.controller;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.dto.property.PropertyCreateDto;
import com.scytalys.technikon.dto.property.PropertyUpdateDto;
import com.scytalys.technikon.security.service.UserInfoDetails;
import com.scytalys.technikon.service.PropertyService;
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
@RequestMapping("/api/property")
@AllArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/id/{propertyId}")
    public ResponseEntity<Property> read(@PathVariable long propertyId, Authentication authentication) {
        Property property = propertyService.findPropertyById(propertyId);
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            String authTin = userInfoDetails.getTin();
            if (!authTin.equals(property.getPropertyOwner().getTin())) throw new AccessDeniedException("You are not authorized to view a property of another user.");
        }
        return ResponseEntity.ok(property);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/properties/{propertyOwnerId}")
    public ResponseEntity<List<Property>> findAllProperties(@PathVariable Long propertyOwnerId, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            Long authId = userInfoDetails.getId();
            if (!authId.equals(propertyOwnerId)) throw new AccessDeniedException("You are not authorized to add a property to a another user.");
        }
        List<Property> properties = propertyService.findAllProperties().stream()
                .filter(property -> property.getPropertyOwner().getId().equals(propertyOwnerId))
                .toList();

        return ResponseEntity.ok(properties);
    }
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/tin/{tin}")
    public ResponseEntity<Property> readByTin(@PathVariable String tin, Authentication authentication) {
        Property property = propertyService.findPropertyByTin(tin);
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            long authId = userInfoDetails.getId();
            if (authId!=property.getPropertyOwner().getId()) throw new AccessDeniedException("You are not authorized to view a property of another user.");
        }
        return property == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(property);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/propertyArea/{longitude}/{latitude}")
    public ResponseEntity<List<Property>> readByArea(@PathVariable double longitude, @PathVariable double latitude, Authentication authentication) {
        List<Property> properties = propertyService.findByArea(longitude, latitude);
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            String authTin = userInfoDetails.getTin();
            if (!authTin.equals(properties.get(0).getPropertyOwner().getTin())) throw new AccessDeniedException("You are not authorized to view a property of another user.");
        }
        return properties == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(properties);
    }


    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    ResponseEntity<PropertyCreateDto> createProperty(@RequestBody PropertyCreateDto propertyCreateDto, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            Long authId = userInfoDetails.getId();
            if (!authId.equals(propertyCreateDto.getPropertyOwnerId())) throw new AccessDeniedException("You are not authorized to add a property to a another user.");
        }
        PropertyCreateDto propertyCreateDto1 = propertyService.createProperty(propertyCreateDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "Creation of a property");
        return new ResponseEntity<>(propertyCreateDto1, headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAllProperties/")
    public List<Property> read() {
        return propertyService.findAllProperties();
    }
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{propertyId}")
    public ResponseEntity<PropertyUpdateDto> updateProperty(
            @PathVariable("propertyId") long propertyId,
            @RequestBody PropertyUpdateDto propertyUpdateDto,
            Authentication authentication ){

        PropertyUpdateDto updatedProperty = propertyService.updateProperty(propertyId, propertyUpdateDto);

        if (updatedProperty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProperty);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{propertyId}")
    public ResponseEntity<Object> eraseProperty(@PathVariable("propertyId") long propertyId, Authentication authentication) {
        Property property = propertyService.findPropertyById(propertyId);
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))  {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            String authTin = userInfoDetails.getTin();
            if (!authTin.equals(property.getPropertyOwner().getTin())) throw new AccessDeniedException("You are not authorized to delete a property of another user.");
        }
        boolean result =  propertyService.checkRelatedEntries(property);
        if (result) propertyService.eraseProperty(propertyId);
        else propertyService.deactivateProperty(property);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

