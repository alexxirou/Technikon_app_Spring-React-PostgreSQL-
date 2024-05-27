package com.scytalys.technikon.service;

import com.scytalys.technikon.domain.Admin;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.repository.AdminRepository;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

public interface AdminService {
    List<Admin> findAll();
    Admin findById(Long id);
    Admin create(Admin admin);
    Admin update(Long id, Admin admin);
    void delete(Long id);

    List<PropertyOwner> getRegisteredOwners(LocalDate from, LocalDate to);
    List<Property> getRegisteredProperties(PropertyOwner owner);
    List<PropertyRepair> getDueRepairs(Property property);

    void deleteOwner(String tin);

    PropertyOwner createOwner(PropertyOwner propertyOwner);
    PropertyOwner findOwner(String tin);
    Property findProperty(String tin);

}
