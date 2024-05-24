package com.scytalys.technikon.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DateController {

    @GetMapping("/api/time")
    public long getCurrentTimestamp(Authentication authentication) {
        return System.currentTimeMillis();
    }
}
