package com.scytalys.technikon.controller;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.dto.property.PropertyCreateDto;
import com.scytalys.technikon.dto.property.PropertyUpdateDto;
import com.scytalys.technikon.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property")
@AllArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    //Get Property id
    @GetMapping("/id/{propertyId}")
    public ResponseEntity<Property> read(@PathVariable long propertyId) {
        Property property = propertyService.findPropertyById(propertyId);
        return ResponseEntity.ok(property);
    }

    //Get all Properties
    @GetMapping("/properties")
    public List<Property> findAllProperties() {
        return propertyService.findAllProperties();
    }

    //Get Property Tin
    @GetMapping("/tin/{propertyTin}")
    public ResponseEntity<Property> readByTin(@PathVariable String tin) {
        Property property = propertyService.findPropertyByTin(tin);
        return property == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(property);
    }

    //Get Property longitude & latitude
    @GetMapping("/propertyArea/{longitude}/{latitude}")
    public ResponseEntity<List<Property>> readByArea(@PathVariable double longitude, @PathVariable double latitude) {
        List<Property> properties = propertyService.findByArea(longitude, latitude);
        return properties == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(properties);
    }


    //CRUD
    //Create
    @PostMapping("/create")
    ResponseEntity<PropertyCreateDto> createProperty(@RequestBody PropertyCreateDto propertyDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "Creation of a property");
        return new ResponseEntity<>(propertyService.createProperty(propertyDto), headers, HttpStatus.CREATED);
    }

    //Update
    @GetMapping("/getAllProperties/")
    public List<Property> read() {
        return propertyService.findAllProperties();
    }

    @PutMapping("/update/{propertyId}")
    public ResponseEntity<PropertyUpdateDto> updateProperty(
            @PathVariable("propertyId") long propertyId,
            @RequestBody PropertyUpdateDto propertyUpdateDto) {

        PropertyUpdateDto updatedProperty = propertyService.updateProperty(propertyId, propertyUpdateDto);
        if (updatedProperty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProperty);
    }

//    //Deactivate
//    @PutMapping("/property/deactivate/{propertyId}")
//    public ResponseEntity<PropertyUpdateDto> deactivateProperty(
//            @PathVariable("propertyId") long propertyId,
//            @RequestBody PropertyDeactivateDto propertyDeactivateDto) {
//        PropertyUpdateDto deactivatedPropertyDto = propertyService.deactivateProperty(propertyId, propertyDeactivateDto);
//        if (deactivatedPropertyDto == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(deactivatedPropertyDto);
//    }
//
//    //Erase
//    @DeleteMapping("/property/{propertyId}")
//    public ResponseEntity<Void> eraseProperty(@PathVariable("propertyId") long propertyId) {
//        boolean isDeleted = propertyService.eraseProperty(propertyId);
//        if (isDeleted) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}

