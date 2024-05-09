package com.scytalys.technikon.mapper;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserCreationDto;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.dto.UserUpdateDto;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.service.impl.PropertyOwnerServiceImpl;
import com.scytalys.technikon.service.impl.PropertyServiceImpl;
import jakarta.validation.UnexpectedTypeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        propertyOwner.setId(3L); // id
        propertyOwner.setName("Johny"); // name
        propertyOwner.setSurname("Doep"); // surname
        propertyOwner.setEmail("jdee@hotmail.com"); // email
        propertyOwner.setUsername("jdee"); // username
        propertyOwner.setPassword("pass"); // password
        propertyOwner.setAddress("somewhere"); // address
        propertyOwner.setPhoneNumber("999582486");// phoneNumber
        propertyOwner.setVersion(0);
        createUserDto = new UserCreationDto(3L, "Johny", "Doep", "jdee@hotmail.com", "jdee", "pass", "somewhere", "999582486", 0);

    }

    @AfterEach
    public void teardown() {

        propertyOwner = null;
        createUserDto = null;


    }

    @Test
    public void testUserCreationDtoToUser() {


        // Call the method to be tested
        PropertyOwner user =  UserMapper.INSTANCE.userCreationDtoToOwner(createUserDto);

        assertEquals(user, propertyOwner);
    }

    @Test
    public void testUserCreationDtoToUserFail() {


        // Call the method to be tested
        UserCreationDto noIdDto= new UserCreationDto(null, "Johny", "Doep", "jdee@hotmail.com", "jdee", "pass", "somewhere", "999582486", 0);


        assertThrows(IllegalArgumentException.class, () -> {
            UserMapper.INSTANCE.userCreationDtoToOwner(noIdDto);
        });    }

    @Test
    public void testUserToUserResponseDto() {

        // Call the method to be tested
        UserResponseDto userResponseDto = UserMapper.INSTANCE.userToUserResponseDto(propertyOwner);

        assertEquals(userResponseDto.id(), propertyOwner.getId());
        assertEquals(userResponseDto.username(), propertyOwner.getUsername());
        assertEquals(userResponseDto.email(), propertyOwner.getEmail());
        assertEquals(userResponseDto.version(), propertyOwner.getVersion());
    }

    @Test
    public void testUserUpdateDtoToUser() {
        // Create a UserUpdateDto object with sample data
        UserUpdateDto userUpdateDto = new UserUpdateDto(3L,null,"nowhere",null, 0);

        // Call the method to be tested
        PropertyOwner updatedUser = UserMapper.INSTANCE.userUpdateDtoToOwner(userUpdateDto, propertyOwner);

        assertEquals(updatedUser.getId(),userUpdateDto.id());
        assertEquals(updatedUser.getAddress(),userUpdateDto.address());
        assertNotEquals(updatedUser.getEmail(), userUpdateDto.email());
        assertNotEquals(updatedUser.getPassword(), userUpdateDto.password());
        assertEquals(updatedUser.getVersion(),userUpdateDto.version());

    }
}


