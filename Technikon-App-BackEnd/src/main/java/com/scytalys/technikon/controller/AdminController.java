package com.scytalys.technikon.controller;

import com.scytalys.technikon.domain.Admin;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/api/admins/")
public class AdminController {
    private final AdminService adminService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    List<Admin> findAdmins() {
        return adminService.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/find-owners/{fromYear}/{fromMonth}/{fromDay}/{toYear}/{toMonth}/{toDay}/")
    List<PropertyOwner> findOwners(@PathVariable String fromYear, @PathVariable String fromMonth, @PathVariable String fromDay,
                                   @PathVariable String toYear, @PathVariable String toMonth, @PathVariable String toDay) {

        LocalDate from = LocalDate.of(Integer.parseInt(fromYear), Integer.parseInt(fromMonth), Integer.parseInt(fromDay));
        LocalDate to   = LocalDate.of(Integer.parseInt(toYear),   Integer.parseInt(toMonth),   Integer.parseInt(toDay));

        return adminService.getRegisteredOwners(from, to);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/find-properties/{ownerTin}/")
    List<Property> findProperties(@PathVariable String ownerTin) {
        return adminService.getRegisteredProperties(adminService.findOwner(ownerTin));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/find-repairs/{propertyTin}/")
    List<PropertyRepair> findRepairs(@PathVariable String propertyTin) {
        return adminService.getDueRepairs(adminService.findProperty(propertyTin));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete-owner/{ownerTin}/")
    void deleteOwner(@PathVariable String ownerTin) {
        adminService.deleteOwner(ownerTin);
    }
}
