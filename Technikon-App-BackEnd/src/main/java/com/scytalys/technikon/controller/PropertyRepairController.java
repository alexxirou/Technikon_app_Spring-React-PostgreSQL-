package com.scytalys.technikon.controller;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.dto.PropertyRepairDto;
import com.scytalys.technikon.service.PropertyRepairService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/propertyRepair")
@AllArgsConstructor
public class PropertyRepairController {
    private final PropertyRepairService propertyRepairService;

    @GetMapping("/search")
    public ResponseEntity<List<PropertyRepairDto>> searchPropertyRepairs(@RequestParam long propertyOwnerId){
        return new ResponseEntity<>(propertyRepairService.searchPropertyRepairs(propertyOwnerId), HttpStatus.OK);
    }

    @PostMapping
    public PropertyRepairDto createPropertyRepair(@RequestBody PropertyRepairDto propertyRepairDto) {
        return propertyRepairService.createPropertyRepair(propertyRepairDto);
    }

    @PatchMapping("/update/{propertyRepairId}")
    public ResponseEntity<String> updatePropertyRepair(@PathVariable long propertyRepairId, @RequestBody PropertyRepairDto propertyRepairDto) throws IllegalAccessException {
        String returned = propertyRepairService.updatePropertyRepair(propertyRepairDto, propertyRepairId);
        return new ResponseEntity<>(returned, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{propertyRepairId}")
    public ResponseEntity<String> deletePropertyRepair(@PathVariable long propertyRepairId){
       propertyRepairService.deletePropertyRepair(propertyRepairId);
       HttpHeaders headers= new HttpHeaders();
        headers.add("Success-Message", "User Deleted.");
        return new ResponseEntity<>( headers, HttpStatus.OK);
    }
}
