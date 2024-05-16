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

import java.time.LocalDate;
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

    @GetMapping("/{repairId}")
    public ResponseEntity<PropertyRepairDto> readOne(@PathVariable long repairId){
        PropertyRepairDto propertyRepairDto = propertyRepairService.getPropertyRepair(repairId);
        return propertyRepairDto == null? ResponseEntity.notFound().build(): ResponseEntity.ok(propertyRepairDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PropertyRepairDto>> readAll(){
        List<PropertyRepairDto> propertyRepairDtos = propertyRepairService.getAllPropertyRepairs();
        return propertyRepairDtos == null? ResponseEntity.notFound().build(): ResponseEntity.ok(propertyRepairDtos);
    }

    @GetMapping("/all-by-owner/{propertyOwnerId}")
    public ResponseEntity<List<PropertyRepairDto>> readAllByOwner(@PathVariable long propertyOwnerId) {
        return new ResponseEntity<>(propertyRepairService.getPropertyRepairsByOwner(propertyOwnerId), HttpStatus.OK);
    }

    //Spring Boot does not allow to use GetMapping with RequestBody
    @GetMapping("get-by-date")
    public ResponseEntity<List<PropertyRepairDto>> searchPropertyRepairByDate(@RequestParam long propertyOwnerId, @RequestParam LocalDate date) {
        return new ResponseEntity<>(propertyRepairService.searchPropertyRepairByDate(propertyOwnerId, date), HttpStatus.OK);
    }

    //Spring Boot does not allow to use GetMapping with RequestBody
    @GetMapping("get-by-dates")
    public ResponseEntity<List<PropertyRepairDto>> searchPropertyRepairsByDates(@RequestParam long propertyOwnerId, @RequestParam LocalDate firstDate, @RequestParam LocalDate lastDate) {
        return new ResponseEntity<>(propertyRepairService.searchPropertyRepairsByDates(propertyOwnerId, firstDate, lastDate), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyRepairUpdateDto> update(@PathVariable long id, @RequestBody PropertyRepairUpdateDto dto){
        return new ResponseEntity<>(propertyRepairService.updatePropertyRepair(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePropertyRepair(long id) throws IllegalAccessException {
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
