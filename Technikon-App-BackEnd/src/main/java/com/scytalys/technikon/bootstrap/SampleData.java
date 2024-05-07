package com.scytalys.technikon.bootstrap;


import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.PropertyType;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.dto.PropertyRepairDto;
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
import java.util.Date;

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
            PropertyOwner propertyOwner = new PropertyOwner();
            propertyOwner.setId(2L); // id
            propertyOwner.setName("John"); // name
            propertyOwner.setSurname("Doe"); // surname
            propertyOwner.setEmail("JDE@hotmail.com"); // email
            propertyOwner.setUsername("JDE"); // username
            propertyOwner.setPassword("pass"); // password
            propertyOwner.setAddress("somewhere"); // address
            propertyOwner.setPhoneNumber(999582486);
            propertyOwnerService.createUser(propertyOwner);

            propertyOwnerService.searchUserById(1L);

            Property property = new Property();
            property.setAddress("Filellinon 12");
            property.setPropertyOwner(propertyOwner);
            property.setId(1L);
            property.setPropertyType(PropertyType.MAISONETTE);
            property.setLatitude(13.5);
            property.setLongitude(34.54);
            property.setConstructionYear(LocalDate.of(1998, 10, 12));
            propertyService.createProperty(property);

            PropertyRepairDto propertyRepairDto = new PropertyRepairDto(
                    propertyOwner.getId(),
                    property.getId(),
                    LocalDate.of(2024, 8, 15),
                    "Plumb work",
                    RepairType.PLUMBING,
                    RepairStatus.SCHEDULED,
                    new BigDecimal("12.0"),
                    "Describing with details the work to be done");
            propertyRepairService.createPropertyRepair(propertyRepairDto);
        };
    }
}
