package com.scytalys.technikon.bootstrap;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.*;
import com.scytalys.technikon.domain.category.PropertyType;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.dto.UserCreationDto;
import com.scytalys.technikon.dto.UserSearchDto;
import com.scytalys.technikon.dto.UserSearchResponseDto;
import com.scytalys.technikon.dto.UserUpdateDto;
import com.scytalys.technikon.dto.property.PropertyCreateDto;
import com.scytalys.technikon.mapper.PropertyMapper;
import com.scytalys.technikon.mapper.PropertyRepairMapper;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.service.AdminService;
import com.scytalys.technikon.service.PropertyOwnerService;
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
    private final PropertyMapper propertyMapper;
    private final PropertyRepository propertyRepository;

    private final AdminService adminService;


    @Bean
    public CommandLineRunner myCommandLineRunner() {
        return args -> {
            Logger logger = LoggerFactory.getLogger(CommandLineRunner.class); // Replace MyApplication with your class name


            Admin theAdmin = new Admin();
            theAdmin.setTin("1751614865GR");// id
            theAdmin.setName("Admin"); // name
            theAdmin.setSurname("Administeridis"); // surname
            theAdmin.setEmail("adminphile@hotmail.com"); // email
            theAdmin.setUsername("admin"); // username
            theAdmin.setPassword("pass"); // password
            theAdmin.setAddress("over here"); // address
            theAdmin.setPhoneNumber("+30999582486");
            theAdmin.setRegistrationDate(LocalDate.of(2010, 11, 21));
            adminService.create(theAdmin);


            PropertyOwner propertyOwner = new PropertyOwner();
            propertyOwner.setTin("1651614865GR");// id
            propertyOwner.setName("John"); // name
            propertyOwner.setSurname("Doe"); // surname
            propertyOwner.setEmail("JDE@hotmail.com"); // email
            propertyOwner.setUsername("JDEdfezv"); // username
            propertyOwner.setPassword("pass"); // password
            propertyOwner.setAddress("somewhere"); // address
            propertyOwner.setPhoneNumber("+30999582486");
            propertyOwner.setRegistrationDate(LocalDate.of(2012, 9, 11));
            adminService.createOwner(propertyOwner);
////
            logger.info("Created property owner: {}", propertyOwner);
            Property property = new Property();
            property.setTin("12345678910");
            property.setAddress("somewhere");
            property.setPropertyType(PropertyType.values()[1]);
            property.setConstructionYear(LocalDate.ofEpochDay(2000));
            property.setLatitude(10.5);
            property.setLongitude(58.4);
            property.setPropertyOwner(propertyOwner);
            property.setPicture("fjrehgf");
            PropertyCreateDto propertyCreateDto = propertyMapper.toPropertyCreateDto(property);
            propertyService.createProperty(propertyCreateDto);
            property = propertyService.findPropertyByTin(property.getTin());

//
//            System.out.println(property.getId());
//            System.out.println(property);
//            logger.info("Created property: {}", property);
//            System.out.println(propertyService.findPropertyById(1L));
//
            PropertyRepair propertyRepair = new PropertyRepair();
            propertyRepair.setDateOfRepair(LocalDate.of(2024, 6, 14));
            propertyRepair.setShortDescription("Plumb work");
            propertyRepair.setRepairType(RepairType.PLUMBING);
            propertyRepair.setRepairStatus(RepairStatus.SCHEDULED);
            propertyRepair.setCost(new BigDecimal(150));
            propertyRepair.setLongDescription("Describing with details the work to be done");
            propertyRepair.setProperty(property);
//
//
//            logger.info("Created property repair: {}", propertyRepair);
//
//            UserSearchDto request = new UserSearchDto("1651614865GR", null, null);
//            List<PropertyOwner> resultUser = propertyOwnerService.searchUser(request);
//            logger.info("Searched property Owner: {}", resultUser);
//            List<UserSearchResponseDto> responseDto = propertyOwnerService.createSearchResponse(resultUser);
//            logger.info("Created user search response: {}", responseDto);
//            UserUpdateDto newUpdate = new UserUpdateDto(null, "elsewhere", null, propertyOwner.getVersion());
//            propertyOwnerService.updateUser(propertyOwner.getTin(), newUpdate);
//            logger.info("Updated user with: {}", newUpdate);
//            resultUser = propertyOwnerService.searchUser(request);
//            logger.info("Searched property Owner: {}", resultUser);
//
//
//
            Property property1 = new Property();
            property1.setAddress("Filellinon 12");
            property1.setTin("98765432100");
            property1.setPropertyOwner(propertyOwner);
            property1.setPropertyType(PropertyType.MAISONETTE);
            property1.setLatitude(13.5);
            property1.setLongitude(34.54);
            property1.setConstructionYear(LocalDate.of(1998, 10, 12));
            PropertyCreateDto propertyCreateDto2 = propertyMapper.toPropertyCreateDto(property1);
            propertyService.createProperty(propertyCreateDto2);

//            Property property2 = new Property();
//            property2.setAddress("Mesologgiou 12");
//            property2.setPropertyOwner(propertyOwner);
//            property2.setPropertyType(PropertyType.APARTMENT_BUILDING);
//            property2.setLatitude(13);
//            property2.setLongitude(13);
//            property2.setConstructionYear(LocalDate.of(2000, 2, 2));
//            PropertyCreateDto propertyCreateDto3 = propertyMapper.toPropertyCreateDto(property2);
//            propertyService.createProperty(propertyCreateDto3);
//
//            PropertyRepair propertyRepair1 = new PropertyRepair();
//            propertyRepair1.setPropertyOwner(propertyOwner);
//            propertyRepair1.setProperty(property);
//            propertyRepair1.setCost(new BigDecimal(150));
//            propertyRepair1.setRepairType(RepairType.PLUMBING);
//            propertyRepair1.setRepairStatus(RepairStatus.SCHEDULED);
//            propertyRepair1.setDateOfRepair(LocalDate.of(2025,5,5));
//            propertyRepair1.setShortDescription("short description for property repair with id 1");
//            propertyRepair1.setLongDescription("long description for property repair with id 1");
//            propertyRepairService.createPropertyRepair(propertyRepairMapper.RepairToPropertyRepairDto(propertyRepair1));
//
//            PropertyOwner propertyOwner1 = new PropertyOwner();
//            propertyOwner1.setTin("1651614866GR");// id
//            propertyOwner1.setName("John"); // name
//            propertyOwner1.setSurname("Doe"); // surname
//            propertyOwner1.setEmail("JDE5@hotmail.com"); // email
//            propertyOwner1.setUsername("JDEdfezvkl"); // username
//            propertyOwner1.setPassword("passw"); // password
//            propertyOwner1.setAddress("somewheree"); // address
//            propertyOwner1.setPhoneNumber("+30999582487");
//
//            UserCreationDto dto1 =new UserCreationDto(propertyOwner1.getTin(), propertyOwner1.getName(), propertyOwner1.getSurname(), propertyOwner1.getEmail(), propertyOwner1.getUsername(), propertyOwner1.getPassword(), propertyOwner1.getAddress(), propertyOwner1.getPhoneNumber());
//            propertyOwner1=propertyOwnerService.createDBUser(dto1);
//
//            PropertyRepair propertyRepair2 = new PropertyRepair();
//            propertyRepair2.setProperty(property1);
//            propertyRepair2.setPropertyOwner(propertyOwner1);
//            propertyRepair2.setCost(new BigDecimal(200));
//            propertyRepair2.setRepairStatus(RepairStatus.DEFAULT_PENDING);
//            propertyRepair2.setRepairType(RepairType.ELECTRICAL_WORK);
//            propertyRepair2.setDateOfRepair(LocalDate.of(2025,8,8));
//            propertyRepair2.setShortDescription("short description for repair 2");
//            propertyRepair2.setLongDescription("long description for repair2");
//            propertyRepairService.createPropertyRepair(propertyRepairMapper.RepairToPropertyRepairDto(propertyRepair2));
//
//            PropertyRepair propertyRepair3 = new PropertyRepair();
//            propertyRepair3.setProperty(property1);
//            propertyRepair3.setPropertyOwner(propertyOwner1);
//            propertyRepair3.setCost(new BigDecimal(140));
//            propertyRepair3.setRepairStatus(RepairStatus.DEFAULT_PENDING);
//            propertyRepair3.setRepairType(RepairType.ELECTRICAL_WORK);
//            propertyRepair3.setDateOfRepair(LocalDate.of(2025,10,10));
//            propertyRepair3.setShortDescription("short description for repair 3");
//            propertyRepair3.setLongDescription("long description for repair3");
//            propertyRepairService.createPropertyRepair(propertyRepairMapper.RepairToPropertyRepairDto(propertyRepair3));
        };
    }
    }