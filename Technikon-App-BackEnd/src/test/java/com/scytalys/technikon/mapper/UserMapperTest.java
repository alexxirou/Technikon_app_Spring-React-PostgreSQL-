package com.scytalys.technikon.mapper;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.dto.UserCreationDto;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.dto.UserSearchResponseDto;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.service.impl.PropertyOwnerServiceImpl;
import com.scytalys.technikon.service.impl.PropertyServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class UserMapperTest {
    @InjectMocks
    private PropertyOwnerServiceImpl propertyOwnerService;


    @Mock
    private PropertyOwnerRepository propertyOwnerRepository;

    @InjectMocks
    PropertyServiceImpl propertyService;


    private PropertyOwner propertyOwner;
    private UserCreationDto createUserDto;


    @BeforeEach
    public void setup() {

        MockitoAnnotations.openMocks(this);

        propertyOwner = new PropertyOwner();
        propertyOwner.setId(3145648468468L);
        propertyOwner.setTin("3145648468468GR");
        propertyOwner.setName("Johny"); // name
        propertyOwner.setSurname("Doep"); // surname
        propertyOwner.setEmail("jdee@hotmail.com"); // email
        propertyOwner.setUsername("jdeed"); // username
        propertyOwner.setPassword("pass"); // password
        propertyOwner.setAddress("somewhere"); // address
        propertyOwner.setPhoneNumber("+309099582486");// phoneNumber
        propertyOwner.setVersion(0);
        createUserDto = new UserCreationDto("3145648468468GR", "Johny", "Doep", "jdee@hotmail.com", "jdeed", "pass", "somewhere", "+309099582486");

    }

    @AfterEach
    public void tearDown() {

        propertyOwner = null;
        createUserDto = null;


    }

    @Test
    public void testUserCreationDtoToUser() {


        // Call the method to be tested
       PropertyOwner user = OwnerMapper.INSTANCE.userCreationDtoToPropertyOwner(createUserDto);
       user.setId(3145648468468L);
       assertEquals(user, propertyOwner);
    }

    @Test
    public void testUserCreationDtoToUserFailNullField() {


        // Call the method to be tested
        UserCreationDto noIdDto= new UserCreationDto(null, "Johny", "Doep", "jdee@hotmail.com", "jdee", "pass", "somewhere", "+309099582486");
        assertThrows(IllegalArgumentException.class, () -> {
            OwnerMapper.INSTANCE.userCreationDtoToPropertyOwner(noIdDto);
        });    }


    @Test
    public void testUserCreationDtoToUserFailInvalidFieldEmail() {


        // Call the method to be tested
        UserCreationDto noIdDto= new UserCreationDto("3145648468468GR", "Johny", "Doep", "jdee@gfoefe", "jdee", "pass", "somewhere", "+309099582486");
        assertThrows(IllegalArgumentException.class, () -> {
            OwnerMapper.INSTANCE.userCreationDtoToPropertyOwner(noIdDto);
        });    }

    @Test
    public void testUserToUserResponseDto() {

        // Call the method to be tested
        UserResponseDto userResponseDto = OwnerMapper.INSTANCE.userToUserResponseDto(propertyOwner);
        assertEquals(userResponseDto.tin(), propertyOwner.getTin());
        assertEquals(userResponseDto.username(), propertyOwner.getUsername());
        assertEquals(userResponseDto.email(), propertyOwner.getEmail());
        assertEquals(userResponseDto.version(), propertyOwner.getVersion());
    }
    @Test
    public void testUserSearchResponseDtoCreation(){
        UserSearchResponseDto dto = OwnerMapper.INSTANCE.userToUserSearchResponseDto(propertyOwner);
        assertEquals(propertyOwner.getTin(), dto.tin());
        assertEquals(propertyOwner.getUsername(), dto.username());
        assertEquals(propertyOwner.getEmail(), dto.email());
        assertEquals(propertyOwner.getName(), dto.name());
        assertEquals(propertyOwner.getSurname(), dto.surname());
        assertEquals(propertyOwner.getAddress(), dto.address());
        assertEquals(propertyOwner.getPhoneNumber(), dto.phoneNumber());

    }

}


