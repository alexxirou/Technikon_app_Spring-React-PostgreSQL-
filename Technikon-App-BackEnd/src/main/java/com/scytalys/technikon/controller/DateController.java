package com.scytalys.technikon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DateController {

    @GetMapping("/api/time")
    public ResponseEntity<Long> getCurrentTimestamp(Authentication authentication) {
        return new ResponseEntity<>(System.currentTimeMillis(), HttpStatus.OK);
    }
}
