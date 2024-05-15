package com.scytalys.technikon.bootstrap;


import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.category.PropertyType;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.dto.UserCreationDto;
import com.scytalys.technikon.dto.UserSearchDto;
import com.scytalys.technikon.dto.UserSearchResponseDto;
import com.scytalys.technikon.dto.UserUpdateDto;
import com.scytalys.technikon.service.PropertyOwnerService;
import com.scytalys.technikon.dto.repair.PropertyRepairDto;
import com.scytalys.technikon.service.PropertyRepairService;
import com.scytalys.technikon.service.PropertyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Configuration
@Slf4j
public class SampleData {
    private final PropertyRepairService propertyRepairService;
    private final PropertyOwnerService propertyOwnerService;
    private final PropertyService propertyService;

    @Bean
    public CommandLineRunner myCommandLineRunner() {
        return args -> {
            Logger logger = LoggerFactory.getLogger(CommandLineRunner.class); // Replace MyApplication with your class name

            PropertyOwner propertyOwner = new PropertyOwner();
            propertyOwner.setTin("1651614865GR");// id
            propertyOwner.setName("John"); // name
            propertyOwner.setSurname("Doe"); // surname
            propertyOwner.setEmail("JDE@hotmail.com"); // email
            propertyOwner.setUsername("JDEdfezv"); // username
            propertyOwner.setPassword("pass"); // password
            propertyOwner.setAddress("somewhere"); // address
            propertyOwner.setPhoneNumber("+30999582486");

            UserCreationDto dto =new UserCreationDto(propertyOwner.getTin(), propertyOwner.getName(), propertyOwner.getSurname(), propertyOwner.getEmail(), propertyOwner.getUsername(), propertyOwner.getPassword(), propertyOwner.getAddress(), propertyOwner.getPhoneNumber());
            logger.info("Created user creation dto: {}", dto);

            propertyOwner=propertyOwnerService.createDBUser(dto);
            logger.info("Created property owner: {}", propertyOwner);
//            propertyOwnerService.updateUserPassword(propertyOwner.getId(),"password", propertyOwner.getVersion());
            Property property = new Property();

            property.setTin("15161651616fr");
            property.setAddress("somewhere");
            property.setPropertyType(PropertyType.values()[1]);
            property.setLatitude(10.5);
            property.setLongitude(58.4);
            property.setPropertyOwner(propertyOwner);

            propertyService.createProperty(property);
            logger.info("Created property: {}", property);
            Property result = propertyService.searchProperty(1L);
            logger.info("Created result search response: {}", result);


            UserSearchDto request =new UserSearchDto("1651614865GR",null,null);
            List<PropertyOwner> resultUser =propertyOwnerService.searchUser(request);
            logger.info("Searched property Owner: {}", resultUser);
            List<UserSearchResponseDto> responseDto=propertyOwnerService.createSearchResponse(resultUser);
            logger.info("Created user search response: {}", responseDto);
            UserUpdateDto newUpdate = new UserUpdateDto(null,"elsewhere",null, propertyOwner.getVersion());
            propertyOwnerService.UpdateUser(propertyOwner.getTin(), newUpdate);
            logger.info("Updated user with: {}", newUpdate);
            resultUser =propertyOwnerService.searchUser(request);
            logger.info("Searched property Owner: {}", resultUser);
            Property property1 = new Property();
            property1.setAddress("Filellinon 12");
            property1.setPropertyOwner(propertyOwner);
            property1.setId(1);
            property1.setPropertyType(PropertyType.MAISONETTE);
            property1.setLatitude(13.5);
            property1.setLongitude(34.54);
            property1.setConstructionYear(LocalDate.of(1998, 10, 12));
            propertyService.createProperty(property1);

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