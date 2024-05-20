package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.Admin;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.repository.AdminRepository;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.service.AdminService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final PropertyOwnerRepository propertyOwnerRepository;
    private final PropertyRepository propertyRepository;

    // private final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin findById(Long id) {
        return adminRepository.findById(id).get();
    }

    @Override
    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin update(Long id, Admin admin) {
        delete(id);
        return create(admin);
    }

    @Override
    public void delete(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public List<PropertyOwner> getRegisteredOwners(LocalDate from, LocalDate to) {
        List<PropertyOwner> owners = propertyOwnerRepository.findAll();
        List<PropertyOwner> filteredOwners = new LinkedList<>();

        for (PropertyOwner owner : owners)
            if (!owner.getRegistrationDate().isBefore(from)
                && !owner.getRegistrationDate().isAfter(to))
                    filteredOwners.add(owner);

        return filteredOwners;
    }

    @Override
    public PropertyOwner createOwner(PropertyOwner propertyOwner) {
        return propertyOwnerRepository.save(propertyOwner);
    }

    @Override
    public List<Property> getRegisteredProperties(PropertyOwner owner, LocalDate from, LocalDate to) {
        List<Property> properties = propertyRepository.findAll();
        List<Property>  filterdProperties = new LinkedList<Property>();
        for (Property property : properties)
            if (property.getPropertyOwner().equals(owner)
                && !property.getRegistrationDate().isBefore(from)
                && !property.getRegistrationDate().isAfter(to))
                    filterdProperties.add(property);
        return properties;
    }
}
