package com.scytalys.technikon.controller;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.service.PropertyRepairService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/propertyRepair")
@AllArgsConstructor
public class PropertyRepairController {
    private final PropertyRepairService propertyRepairService;

    @GetMapping
    public String hello() {return "Hello World!";}

    @PostMapping
    public PropertyRepair createPropertyRepair(@RequestBody PropertyRepair propertyRepair){
        return propertyRepairService.createPropertyRepair(propertyRepair);
    }


}
