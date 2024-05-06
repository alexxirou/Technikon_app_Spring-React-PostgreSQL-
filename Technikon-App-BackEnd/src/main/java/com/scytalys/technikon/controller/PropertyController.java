package com.scytalys.technikon.controller;

import com.scytalys.technikon.dto.PropertyDto;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/property")
@AllArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @GetMapping()
    public Property searchProperty(@RequestBody PropertyDto propertyDto){
        return propertyService.searchProperty(propertyDto.propertyId());
    }

//    @PostMapping
//    public Property createProperty(@RequestBody PropertyDto propertyDto)


}
