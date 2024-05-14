package com.scytalys.technikon.controller;

import com.scytalys.technikon.dto.PropertyDto;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/property")
@AllArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping("/property")
    public ResponseEntity<Property> createProperty(@RequestBody Property property){
        return ResponseEntity.ok(propertyService.createProperty(property));
    }

    @GetMapping("/propertySearch")
    public Property searchProperty(@RequestBody PropertyDto propertyDto){
        return propertyService.searchProperty(propertyDto.propertyId());
    }

    @PostMapping
    public Property updateProperty(@RequestBody PropertyDto propertyDto){return null;}

    @PostMapping
    public Property deactivateProperty(@RequestBody PropertyDto propertyDto){return null;}


}
