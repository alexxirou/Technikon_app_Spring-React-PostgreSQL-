package com.scytalys.technikon.controller;

import com.scytalys.technikon.dto.repair.*;
import com.scytalys.technikon.service.PropertyRepairService;
import com.scytalys.technikon.utility.HeaderUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/property-repairs")
@AllArgsConstructor
public class PropertyRepairController {
    private final PropertyRepairService propertyRepairService;

    @PostMapping("")
    public ResponseEntity<PropertyRepairDto> create(@RequestBody PropertyRepairDto propertyRepairDto) {
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Property repair created successfully.");
        return new ResponseEntity<>(propertyRepairService.createPropertyRepair(propertyRepairDto),headers, HttpStatus.CREATED);
    }

    @GetMapping("/{repairId}")
    public ResponseEntity<PropertyRepairDto> readOne(@PathVariable long repairId){
        PropertyRepairDto propertyRepairDto = propertyRepairService.getPropertyRepair(repairId);
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Property repair found.");
        return new ResponseEntity<>(propertyRepairDto, headers, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PropertyRepairDto>> readAll(){
        List<PropertyRepairDto> propertyRepairDtos = propertyRepairService.getAllPropertyRepairs();
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Property repairs found.");
        return new ResponseEntity<>(propertyRepairDtos, headers, HttpStatus.OK);
    }

    @GetMapping("/all-by-owner/{propertyOwnerId}")
    public ResponseEntity<List<PropertyRepairDto>> readAllByOwner(@PathVariable long propertyOwnerId) {
        List<PropertyRepairDto> propertyRepairDtos = propertyRepairService.getPropertyRepairsByOwner(propertyOwnerId);
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Property repairs found.");
        return new ResponseEntity<>(propertyRepairDtos,headers, HttpStatus.OK);
    }

    //Spring Boot does not allow to use GetMapping with RequestBody
    @GetMapping("/get-by-date")
    public ResponseEntity<List<PropertyRepairDto>> searchPropertyRepairsByDate(@RequestParam long propertyOwnerId, @RequestParam LocalDate date) {
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Property repairs found.");
        return new ResponseEntity<>(propertyRepairService.searchPropertyRepairsByDate(propertyOwnerId, date), headers, HttpStatus.OK);
    }

    //Spring Boot does not allow to use GetMapping with RequestBody
    @GetMapping("/get-by-dates")
    public ResponseEntity<List<PropertyRepairDto>> searchPropertyRepairsByDates(@RequestParam long propertyOwnerId, @RequestParam LocalDate firstDate, @RequestParam LocalDate lastDate) {
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Property repairs found.");
        return new ResponseEntity<>(propertyRepairService.searchPropertyRepairsByDates(propertyOwnerId, firstDate, lastDate), headers, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyRepairUpdateDto> update(@PathVariable long id, @RequestBody PropertyRepairUpdateDto dto){
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Property repair updated.");
        return new ResponseEntity<>(propertyRepairService.updatePropertyRepair(id, dto), headers, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePropertyRepair(long id) {
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "Property repair deleted.");
        propertyRepairService.deletePropertyRepair(id);
        return new ResponseEntity<>(headers,HttpStatus.OK);
    }
}
