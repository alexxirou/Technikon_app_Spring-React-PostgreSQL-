package com.scytalys.technikon.bootstrap;


import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.PropertyType;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.dto.UserCreationDto;
import com.scytalys.technikon.dto.UserSearchDto;
import com.scytalys.technikon.dto.UserSearchResponseDto;
import com.scytalys.technikon.dto.UserUpdateDto;
import com.scytalys.technikon.mapper.PropertyRepairMapper;
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
    private final PropertyRepairMapper propertyRepairMapper;

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
            property.setConstructionYear(LocalDate.ofEpochDay(2000));
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


// DATA FOR PROPERTY REPAIR DEBUGGUING

            Property property1 = new Property();
            property1.setAddress("Filellinon 12");
            property1.setPropertyOwner(propertyOwner);
            property1.setPropertyType(PropertyType.MAISONETTE);
            property1.setLatitude(13.5);
            property1.setLongitude(34.54);
            property1.setConstructionYear(LocalDate.of(1998, 10, 12));
            propertyService.createProperty(property1);

            Property property2 = new Property();
            property2.setAddress("Mesologgiou 12");
            property2.setPropertyOwner(propertyOwner);
            property2.setPropertyType(PropertyType.APARTMENT_BUILDING);
            property2.setLatitude(13);
            property2.setLongitude(13);
            property2.setConstructionYear(LocalDate.of(2000, 2, 2));
            propertyService.createProperty(property2);

            PropertyRepair propertyRepair1 = new PropertyRepair();
            propertyRepair1.setPropertyOwner(propertyOwner);
            propertyRepair1.setProperty(property);
            propertyRepair1.setCost(new BigDecimal(150));
            propertyRepair1.setRepairType(RepairType.PLUMBING);
            propertyRepair1.setRepairStatus(RepairStatus.SCHEDULED);
            propertyRepair1.setDateOfRepair(LocalDate.of(2025,5,5));
            propertyRepair1.setShortDescription("short description for property repair with id 1");
            propertyRepair1.setLongDescription("long description for property repair with id 1");
            propertyRepairService.createPropertyRepair(propertyRepairMapper.RepairToPropertyRepairDto(propertyRepair1));

            PropertyOwner propertyOwner1 = new PropertyOwner();
            propertyOwner1.setTin("1651614866GR");// id
            propertyOwner1.setName("John"); // name
            propertyOwner1.setSurname("Doe"); // surname
            propertyOwner1.setEmail("JDE5@hotmail.com"); // email
            propertyOwner1.setUsername("JDEdfezvkl"); // username
            propertyOwner1.setPassword("passw"); // password
            propertyOwner1.setAddress("somewheree"); // address
            propertyOwner1.setPhoneNumber("+30999582487");

            UserCreationDto dto1 =new UserCreationDto(propertyOwner1.getTin(), propertyOwner1.getName(), propertyOwner1.getSurname(), propertyOwner1.getEmail(), propertyOwner1.getUsername(), propertyOwner1.getPassword(), propertyOwner1.getAddress(), propertyOwner1.getPhoneNumber());
            propertyOwner1=propertyOwnerService.createDBUser(dto1);

            PropertyRepair propertyRepair2 = new PropertyRepair();
            propertyRepair2.setProperty(property1);
            propertyRepair2.setPropertyOwner(propertyOwner1);
            propertyRepair2.setCost(new BigDecimal(200));
            propertyRepair2.setRepairStatus(RepairStatus.DEFAULT_PENDING);
            propertyRepair2.setRepairType(RepairType.ELECTRICAL_WORK);
            propertyRepair2.setDateOfRepair(LocalDate.of(2025,8,8));
            propertyRepair2.setShortDescription("short description for repair 2");
            propertyRepair2.setLongDescription("long description for repair2");
            propertyRepairService.createPropertyRepair(propertyRepairMapper.RepairToPropertyRepairDto(propertyRepair2));

            PropertyRepair propertyRepair3 = new PropertyRepair();
            propertyRepair3.setProperty(property1);
            propertyRepair3.setPropertyOwner(propertyOwner1);
            propertyRepair3.setCost(new BigDecimal(140));
            propertyRepair3.setRepairStatus(RepairStatus.DEFAULT_PENDING);
            propertyRepair3.setRepairType(RepairType.ELECTRICAL_WORK);
            propertyRepair3.setDateOfRepair(LocalDate.of(2025,10,10));
            propertyRepair3.setShortDescription("short description for repair 3");
            propertyRepair3.setLongDescription("long description for repair3");
            propertyRepairService.createPropertyRepair(propertyRepairMapper.RepairToPropertyRepairDto(propertyRepair3));
        };
    }
    }