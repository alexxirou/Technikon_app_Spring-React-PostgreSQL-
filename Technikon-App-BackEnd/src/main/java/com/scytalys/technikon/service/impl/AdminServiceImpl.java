package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.Admin;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.repository.AdminRepository;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.repository.PropertyRepairRepository;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final PropertyOwnerRepository propertyOwnerRepository;
    private final PropertyRepository propertyRepository;
    private final PropertyRepairRepository propertyRepairRepository;
    private final PasswordEncoder passwordEncoder;
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

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
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
    public PropertyOwner createOwner(PropertyOwner propertyOwner) {
        return propertyOwnerRepository.save(propertyOwner);
    }

    @Override
    public PropertyOwner findOwner(String tin) {
        return propertyOwnerRepository.findByTin(tin).get();
    }


    @Override
    public List<PropertyRepair> getDueRepairs(Property property) {
        return propertyRepairRepository.getPropertyRepairsByProperty(property.getId());
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
    public Property findProperty(String tin) {
        return propertyRepository.findByTin(tin);
    }

    @Override
    public List<Property> getRegisteredProperties(PropertyOwner owner) {
        List<Property> properties = propertyRepository.findAll();
        List<Property>  filterdProperties = new LinkedList<Property>();
        for (Property property : properties)
            if (property.getPropertyOwner().equals(owner))
                    filterdProperties.add(property);
        return properties;
    }
}
