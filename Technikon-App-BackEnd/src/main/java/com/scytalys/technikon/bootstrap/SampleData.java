package com.scytalys.technikon.bootstrap;


import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.PropertyType;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.service.PropertyOwnerService;
import com.scytalys.technikon.service.PropertyRepairService;
import com.scytalys.technikon.service.PropertyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Configuration
@Slf4j
public class SampleData {
    @Autowired
    private final PropertyRepairService propertyRepairService;
    @Autowired
    private final PropertyOwnerService propertyOwnerService;
    @Autowired
    private final PropertyService propertyService;
    @Bean
    public CommandLineRunner myCommandLineRunner(){
        return args -> {
            PropertyRepair propertyRepair = new PropertyRepair();
            propertyRepair.setDateOfRepair(LocalDate.of(2024, 6, 14));
            propertyRepair.setShortDescription("Plumb work");
            propertyRepair.setRepairType(RepairType.PLUMBING);
            propertyRepair.setRepairStatus(RepairStatus.SCHEDULED);
            propertyRepair.setCost(new BigDecimal(150));
            propertyRepair.setLongDescription("Describing with details the work to be done");
            propertyRepairService.createPropertyRepair(propertyRepair);

            PropertyOwner propertyOwner = new PropertyOwner();
            propertyOwner.setId(2L); // id
            propertyOwner.setName("John"); // name
            propertyOwner.setSurname("Doe"); // surname
            propertyOwner.setEmail("JDE@hotmail.com"); // email
            propertyOwner.setUsername("JDE"); // username
            propertyOwner.setPassword("pass"); // password
            propertyOwner.setAddress("somewhere"); // address
            propertyOwner.setPhoneNumber("999582486");
            propertyOwnerService.createDBUser(propertyOwner);

//            propertyOwnerService.updateUserPassword(propertyOwner.getId(),"password", propertyOwner.getVersion());
            Property property = new Property();
            property.setId(1L);
            property.setAddress("somewhere");
            property.setPropertyType(PropertyType.values()[1]);
            property.setLatitude(10.5);
            property.setLongitude(58.4);
            property.setPropertyOwner(propertyOwner);
            propertyService.createProperty(property);
            Property result = propertyService.searchProperty(1L);
            System.out.println(result.getId());
        };
    }
}
