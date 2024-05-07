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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/propertyRepair")
@AllArgsConstructor
public class PropertyRepairController {
    private final PropertyRepairService propertyRepairService;

    @PostMapping
    public PropertyRepairDto createPropertyRepair(@RequestBody PropertyRepairDto propertyRepairDto) {
        return propertyRepairService.createPropertyRepair(propertyRepairDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PropertyRepairDto>> searchPropertyRepairs(@RequestParam long propertyOwnerId){
        return new ResponseEntity<>(propertyRepairService.searchPropertyRepairs(propertyOwnerId), HttpStatus.OK);
    }

    @GetMapping("search-by-date")
    public ResponseEntity<List<PropertyRepairDto>> searchPropertyRepairByDate(@RequestParam long propertyOwnerId, LocalDate date){
        return new ResponseEntity<>(propertyRepairService.searchPropertyRepairByDate(propertyOwnerId, date), HttpStatus.OK);
    }

    @GetMapping("search-by-dates")
    public ResponseEntity<List<PropertyRepairDto>> searchPropertyRepairByDates(@RequestParam long propertyOwnerId, LocalDate firstDate, LocalDate lastDate) {
        return new ResponseEntity<>(propertyRepairService.searchPropertyRepairByDates(propertyOwnerId, firstDate, lastDate), HttpStatus.OK);
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
