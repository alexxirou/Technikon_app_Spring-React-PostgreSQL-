package com.scytalys.technikon.controller;

import com.scytalys.technikon.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/users/admins")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/")
    String findOwners() {
        return "another  STUB";
    }

}
