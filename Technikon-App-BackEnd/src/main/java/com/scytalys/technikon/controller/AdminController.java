package com.scytalys.technikon.controller;

import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

//@RestController
//@AllArgsConstructor
//@RequestMapping("/api/v2/users/admins")
//public class AdminController {
//    private final AdminService adminService;
//
//    @GetMapping("/")
//    List<PropertyOwner> findOwners(LocalDate from, LocalDate to) {
//        return adminService.getRegisteredOwners(LocalDate.now(), LocalDate.now());
//    }
//}