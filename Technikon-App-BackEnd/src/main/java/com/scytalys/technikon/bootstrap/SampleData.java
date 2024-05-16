package com.scytalys.technikon.bootstrap;


import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.domain.category.PropertyType;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.dto.UserCreationDto;
import com.scytalys.technikon.dto.UserSearchDto;
import com.scytalys.technikon.dto.UserSearchResponseDto;
import com.scytalys.technikon.dto.UserUpdateDto;
import com.scytalys.technikon.mapper.OwnerMapper;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.security.service.UserInfoService;
import com.scytalys.technikon.service.PropertyOwnerService;
import com.scytalys.technikon.service.PropertyRepairService;
import com.scytalys.technikon.service.PropertyService;
import com.scytalys.technikon.utility.AuthenticationUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

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

    private final UserInfoService userInfoService;
    private Authentication authentication;



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

            propertyOwner=userInfoService.createDBUser(dto);
            logger.info("Created property owner: {}", propertyOwner);
//            propertyOwnerService.updateUserPassword(propertyOwner.getId(),"password", propertyOwner.getVersion());
            authentication = AuthenticationUtils.createAuthentication(propertyOwner.getUsername(), propertyOwner.getPassword());
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
            propertyOwnerService.updateUser(propertyOwner.getTin(), newUpdate, authentication);
            logger.info("Updated user with: {}", newUpdate);
            resultUser =propertyOwnerService.searchUser(request );
            logger.info("Searched property Owner: {}", resultUser);
        };
    }
}
