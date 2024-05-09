package com.scytalys.technikon.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.dto.PropertyRepairCreationDto;
import com.scytalys.technikon.dto.PropertyRepairDto;
import com.scytalys.technikon.exception.InvalidInputException;
import com.scytalys.technikon.service.PropertyRepairService;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/experimental/propertyRepairs/")
@AllArgsConstructor
public class PropertyRepairController {
    private final PropertyRepairService propertyRepairService;

    @PostMapping("create")
    public ResponseEntity<PropertyRepairCreationDto> createPropertyRepair(@RequestBody PropertyRepairCreationDto propertyRepairCreationDto) {
        return new ResponseEntity<>(propertyRepairService.createPropertyRepair(propertyRepairCreationDto), HttpStatus.CREATED);
    }

    @GetMapping("get")
    public ResponseEntity<List<PropertyRepairDto>> searchPropertyRepairs(@Min(1) @RequestParam long propertyOwnerId) {
        return new ResponseEntity<>(propertyRepairService.searchPropertyRepairs(propertyOwnerId), HttpStatus.OK);
    }

    @GetMapping("get-by-date")
    public ResponseEntity<List<PropertyRepairDto>> searchPropertyRepairByDate(@RequestParam long propertyOwnerId, LocalDate date) {
        return new ResponseEntity<>(propertyRepairService.searchPropertyRepairByDate(propertyOwnerId, date), HttpStatus.OK);
    }

    @GetMapping("get-by-dates")
    public ResponseEntity<List<PropertyRepairDto>> searchPropertyRepairByDates(@RequestParam long propertyOwnerId, LocalDate firstDate, LocalDate lastDate) {
        return new ResponseEntity<>(propertyRepairService.searchPropertyRepairByDates(propertyOwnerId, firstDate, lastDate), HttpStatus.OK);
    }

//    @PatchMapping("/update/{propertyOwnerId}/{propertyRepairId}")
//    public ResponseEntity<Object> updatePropertyRepair(@PathVariable long propertyOwnerId, @PathVariable long propertyRepairId, @RequestBody PropertyRepairDto propertyRepairDto) throws IllegalAccessException {
//        propertyRepairService.updatePropertyRepair(propertyOwnerId, propertyRepairId, propertyRepairDto);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @PutMapping("/update-by-date/{propertyRepairId}")
    public ResponseEntity<String> updatePropertyRepairByDate(long propertyRepairId, LocalDate newDate){
        int res =  propertyRepairService.updatePropertyRepairByDate(propertyRepairId, newDate);
        if (res == 0) { //update failed
            throw new DataAccessResourceFailureException("Failed to update date for property repair with id: " + propertyRepairId);
        }
        return new ResponseEntity<>("Property repair updated", HttpStatus.OK);
    }
    @PutMapping("/update-by-shortDescription/{propertyRepairId}")
    public ResponseEntity<String> updatePropertyRepairByShortDescription(long propertyRepairId, String newShortDescription){
        int res =  propertyRepairService.updatePropertyRepairByShortDescription(propertyRepairId, newShortDescription);
        if (res == 0) { //update failed
            throw new DataAccessResourceFailureException("Failed to update short description for property repair with id: " + propertyRepairId);
        }
        return new ResponseEntity<>("Property repair updated", HttpStatus.OK);
    }

    @PutMapping("/update-by-repairType/{propertyRepairId}")
    public ResponseEntity<String> updatePropertyRepairByType(long propertyRepairId, RepairType newRepairType){
        int res =  propertyRepairService.updatePropertyRepairByRepairType(propertyRepairId, newRepairType);
        if (res == 0) { //update failed
            throw new DataAccessResourceFailureException("Failed to update repair type for property repair with id: " + propertyRepairId);
        }
        return new ResponseEntity<>("Property repair updated", HttpStatus.OK);
    }

    @PutMapping("/update-by-cost/{propertyRepairId}")
    public ResponseEntity<String> updatePropertyRepairByCost(long propertyRepairId, BigDecimal newCost){
        int res =  propertyRepairService.updatePropertyRepairByCost(propertyRepairId, newCost);
        if (res == 0) { //update failed
            throw new DataAccessResourceFailureException("Failed to update cost for property repair with id: " + propertyRepairId);
        }
        return new ResponseEntity<>("Property repair updated", HttpStatus.OK);
    }

    @PutMapping("/update-by-longDescription/{propertyRepairId}")
    public ResponseEntity<String> updatePropertyRepairByLongDescription(long propertyRepairId, String newLongDescription){
        int res =  propertyRepairService.updatePropertyRepairByLongDescription(propertyRepairId, newLongDescription);
        if (res == 0) { //update failed
            throw new DataAccessResourceFailureException("Failed to update long description for property repair with id: " + propertyRepairId);
        }
        return new ResponseEntity<>("Property repair updated", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{propertyRepairId}")
    public ResponseEntity<Object> deletePropertyRepair(@RequestParam long propertyOwnerId,@PathVariable long propertyRepairId) {
        propertyRepairService.deletePropertyRepair(propertyOwnerId, propertyRepairId);
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


}
