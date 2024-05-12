package com.scytalys.technikon.controller;


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

    @PutMapping("/update-by-date")
    public ResponseEntity<String> updatePropertyRepairByDate(@RequestBody PropertyRepairUpdateByDateDto propertyRepairUpdateByDateDto){
        int res =  propertyRepairService.updatePropertyRepairByDate(propertyRepairUpdateByDateDto);
        if (res == 0) { //update failed
            throw new DataAccessResourceFailureException("Failed to update date for property repair with id: " + propertyRepairUpdateByDateDto.id());
        }
        return new ResponseEntity<>("Property repair updated", HttpStatus.OK);
    }

    @PutMapping("/update-by-shortDescription")
    public ResponseEntity<String> updatePropertyRepairByShortDescription(@RequestBody PropertyRepairUpdateByShortDescriptionDto dto){
        int res =  propertyRepairService.updatePropertyRepairByShortDescription(dto);
        if (res == 0) { //update failed
            throw new DataAccessResourceFailureException("Failed to update short description for property repair with id: " + dto.id());
        }
        return new ResponseEntity<>("Property repair updated", HttpStatus.OK);
    }

    @PutMapping("/update-by-repairType")
    public ResponseEntity<String> updatePropertyRepairByType(@RequestBody PropertyRepairUpdateByTypeDto dto){
        int res =  propertyRepairService.updatePropertyRepairByType(dto);
        if (res == 0) { //update failed
            throw new DataAccessResourceFailureException("Failed to update repair type for property repair with id: " + dto.id());
        }
        return new ResponseEntity<>("Property repair updated", HttpStatus.OK);
    }

    @PutMapping("/update-by-cost")
    public ResponseEntity<String> updatePropertyRepairByCost(PropertyRepairUpdateByCostDto dto){
        int res =  propertyRepairService.updatePropertyRepairByCost(dto);
        if (res == 0) { //update failed
            throw new DataAccessResourceFailureException("Failed to update cost for property repair with id: " + dto.id());
        }
        return new ResponseEntity<>("Property repair updated", HttpStatus.OK);
    }

    @PutMapping("/update-by-longDescription")
    public ResponseEntity<String> updatePropertyRepairByLongDescription(PropertyRepairUpdateByLongDescriptionDto dto){
        int res =  propertyRepairService.updatePropertyRepairByLongDescription(dto);
        if (res == 0) { //update failed
            throw new DataAccessResourceFailureException("Failed to update long description for property repair with id: " + dto.id());
        }
        return new ResponseEntity<>("Property repair updated", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{propertyOwnerId}/{propertyId}/{id}")
    public ResponseEntity<Object> deletePropertyRepair(@PathVariable long propertyOwnerId, @PathVariable long propertyId, @PathVariable long id) {
        propertyRepairService.deletePropertyRepair(propertyOwnerId, propertyId, id);
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
