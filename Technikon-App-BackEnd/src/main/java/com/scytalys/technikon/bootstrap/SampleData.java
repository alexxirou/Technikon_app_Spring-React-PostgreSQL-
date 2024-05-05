package com.scytalys.technikon.bootstrap;


import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.service.PropertyOwnerService;
import com.scytalys.technikon.service.PropertyRepairService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Configuration
@Slf4j
public class SampleData {

    private final PropertyRepairService propertyRepairService;
    private final PropertyOwnerService propertyOwnerService;
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
            propertyOwnerService.createUser(propertyOwner);

            propertyOwnerService.updateUserPassword(propertyOwner.getId(),"password", propertyOwner.getVersion());
        };
    }
}
