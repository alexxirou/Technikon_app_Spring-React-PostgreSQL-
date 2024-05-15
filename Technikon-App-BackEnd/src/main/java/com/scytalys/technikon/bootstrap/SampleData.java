package com.scytalys.technikon.bootstrap;


import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.category.PropertyType;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.dto.repair.PropertyRepairDto;
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
    public CommandLineRunner myCommandLineRunner() {
        return args -> {
            PropertyOwner propertyOwner = new PropertyOwner();
            propertyOwner.setId(1); // id
            propertyOwner.setName("John"); // name
            propertyOwner.setSurname("Doe"); // surname
            propertyOwner.setEmail("JDE@hotmail.com"); // email
            propertyOwner.setUsername("JDE"); // username
            propertyOwner.setPassword("pass"); // password
            propertyOwner.setAddress("somewhere"); // address
            propertyOwner.setPhoneNumber(999582486);
            propertyOwnerService.createUser(propertyOwner);

            propertyOwnerService.searchUserById(1);

            Property property = new Property();
            property.setAddress("Filellinon 12");
            property.setPropertyOwner(propertyOwner);
            property.setId(1);
            property.setPropertyType(PropertyType.MAISONETTE);
            property.setLatitude(13.5);
            property.setLongitude(34.54);
            property.setConstructionYear(LocalDate.of(1998, 10, 12));
            propertyService.createProperty(property);

            Property property2 = new Property();
            property2.setAddress("Mesologgiou 12");
            property2.setPropertyOwner(propertyOwner);
            property2.setId(2);
            property2.setPropertyType(PropertyType.APARTMENT_BUILDING);
            property2.setLatitude(13);
            property2.setLongitude(13);
            property2.setConstructionYear(LocalDate.of(2000, 2, 2));
            propertyService.createProperty(property2);

            PropertyRepairDto propertyRepairDto1 = new PropertyRepairDto(
                    1,
                    propertyOwner.getId(),
                    property.getId(),
                    LocalDate.of(2025, 5, 5),
                    "short for repair",
                    RepairType.ELECTRICAL_WORK,
                    RepairStatus.SCHEDULED,
                    new BigDecimal(170),
                    "looong");
            propertyRepairService.createPropertyRepair(propertyRepairDto1);

            PropertyRepairDto propertyRepairDto2 = new PropertyRepairDto(
                    2,
                    propertyOwner.getId(),
                    property.getId(),
                    LocalDate.of(2024, 9, 9),
                    "short for repairrrrrr",
                    RepairType.ELECTRICAL_WORK,
                    RepairStatus.DEFAULT_PENDING,
                    new BigDecimal(80),
                    "lllll");
            propertyRepairService.createPropertyRepair(propertyRepairDto2);

            PropertyRepairDto propertyRepairDto3 = new PropertyRepairDto(
                    3,
                    propertyOwner.getId(),
                    2,
                    LocalDate.of(2026, 10, 10),
                    "short for repairrrrrr",
                    RepairType.PLUMBING,
                    RepairStatus.DEFAULT_PENDING,
                    new BigDecimal(80),
                    "lllll");
            propertyRepairService.createPropertyRepair(propertyRepairDto3);
        };
    }
    }