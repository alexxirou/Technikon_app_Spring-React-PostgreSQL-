package com.scytalys.technikon.controller;


import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.dto.repair.*;
import com.scytalys.technikon.exception.InvalidInputException;
import com.scytalys.technikon.exception.ResourceNotFoundException;
import com.scytalys.technikon.service.PropertyRepairService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/property-repairs")
@AllArgsConstructor
public class PropertyRepairController {
    private final PropertyRepairService propertyRepairService;

    @PostMapping("")
    public ResponseEntity<PropertyRepairDto> create(@RequestBody PropertyRepairDto propertyRepairDto) {
        return new ResponseEntity<>(propertyRepairService.createPropertyRepair(propertyRepairDto), HttpStatus.CREATED);
    }

    @GetMapping("/{propertyOwnerId}/{propertyId}/{repairId}")
    public ResponseEntity<PropertyRepairDto> read(@PathVariable long propertyOwnerId, @PathVariable long propertyId, @PathVariable long repairId){
        PropertyRepairDto propertyRepairDto = propertyRepairService.searchPropertyRepair(propertyOwnerId, propertyId, repairId);
        return propertyRepairDto == null? ResponseEntity.notFound().build(): ResponseEntity.ok(propertyRepairDto);
    }

    @GetMapping("/{propertyOwnerId}/{propertyId}")
    public ResponseEntity<List<PropertyRepairDto>> readAll(@PathVariable long propertyOwnerId, @PathVariable long propertyId) {
        return new ResponseEntity<>(propertyRepairService.searchPropertyRepairs(propertyOwnerId, propertyId), HttpStatus.OK);
    }

    //Spring Boot does not allow to use GetMapping with RequestBody
    @PostMapping("get-by-date")
    public ResponseEntity<List<PropertyRepairDto>> searchPropertyRepairByDate(@RequestBody PropertyRepairSearchByDateDto propertyRepairSearchByDateDto) {
        return new ResponseEntity<>(propertyRepairService.searchPropertyRepairByDate(propertyRepairSearchByDateDto), HttpStatus.OK);
    }

    //Spring Boot does not allow to use GetMapping with RequestBody
    @PostMapping("get-by-dates")
    public ResponseEntity<List<PropertyRepairDto>> searchPropertyRepairByDates(@RequestBody PropertyRepairSearchByDatesDto propertyRepairSearchByDates) {
        return new ResponseEntity<>(propertyRepairService.searchPropertyRepairByDates(propertyRepairSearchByDates), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public PropertyRepairUpdateDto update(@PathVariable long id, @RequestBody PropertyRepairUpdateDto dto){
        return propertyRepairService.updatePropertyRepair(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePropertyRepair(long id) {
        propertyRepairService.deletePropertyRepair(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleDataAccessResourceFailureException(DataAccessResourceFailureException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidInputException(InvalidInputException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
