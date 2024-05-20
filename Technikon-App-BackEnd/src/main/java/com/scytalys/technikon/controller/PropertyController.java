package com.scytalys.technikon.controller;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.dto.PropertyDto;
import com.scytalys.technikon.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/property")
@AllArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    //Get Property (entity,id)
    @GetMapping("/{propertyId}")
    public ResponseEntity<Property> read(@PathVariable long propertyId){
        Property property = propertyService.findProperty(propertyId);
        return property == null ? ResponseEntity.notFound().build() :ResponseEntity.ok(property);
    }

    @GetMapping("/product/")
    public List<Property> findAllProperties(){
        return propertyService.findAllProperties();
    }



    //Create
    @PostMapping("")
    public ResponseEntity<Property> createProperty(@RequestBody Property property){
        return ResponseEntity.ok(propertyService.createProperty(property));
    }

    //Search
    @GetMapping("/propertySearch")
    public Property searchProperty(@RequestBody PropertyDto propertyDto){
        return null;
//        return propertyService.searchProperty(propertyDto.propertyId());
    }

    @PostMapping("/propertyUpdate")
    public Property updateProperty(@RequestBody PropertyDto propertyDto){return null;}

    @PostMapping("/propertyDeactivate")
    public Property deactivateProperty(@RequestBody PropertyDto propertyDto){return null;}


}
