package com.scytalys.technikon.bootstrap;


import com.scytalys.technikon.domain.*;
import com.scytalys.technikon.domain.category.PropertyType;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.dto.UserCreationDto;
import com.scytalys.technikon.dto.UserSearchDto;
import com.scytalys.technikon.dto.UserSearchResponseDto;
import com.scytalys.technikon.dto.UserUpdateDto;
import com.scytalys.technikon.mapper.OwnerMapper;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.service.AdminService;
import com.scytalys.technikon.service.PropertyOwnerService;
import com.scytalys.technikon.service.PropertyRepairService;
import com.scytalys.technikon.service.PropertyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final AdminService adminService;


    @Bean
    public CommandLineRunner myCommandLineRunner(){
        return args -> {
            Logger logger = LoggerFactory.getLogger(CommandLineRunner.class); // Replace MyApplication with your class name
            PropertyRepair propertyRepair = new PropertyRepair();
            propertyRepair.setDateOfRepair(LocalDate.of(2024, 6, 14));
            propertyRepair.setShortDescription("Plumb work");
            propertyRepair.setRepairType(RepairType.PLUMBING);
            propertyRepair.setRepairStatus(RepairStatus.SCHEDULED);
            propertyRepair.setCost(new BigDecimal(150));
            propertyRepair.setLongDescription("Describing with details the work to be done");

            propertyRepairService.createPropertyRepair(propertyRepair);
            logger.info("Created property repair: {}", propertyRepair);

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
//            UserCreationDto dto =new UserCreationDto(propertyOwner.getTin(), propertyOwner.getName(), propertyOwner.getSurname(), propertyOwner.getEmail(), propertyOwner.getUsername(), propertyOwner.getPassword(), propertyOwner.getAddress(), propertyOwner.getPhoneNumber());

 //           logger.info("Created user creation dto: {}", dto);

  //          propertyOwner=propertyOwnerService.createDBUser(dto);
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
        };
    }
}
