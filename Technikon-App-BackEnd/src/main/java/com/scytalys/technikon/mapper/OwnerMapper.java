package com.scytalys.technikon.mapper;
import com.scytalys.technikon.domain.Admin;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserCreationDto;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.dto.UserUpdateDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Optional;
import java.util.regex.Pattern;

@Mapper
public interface OwnerMapper {
        OwnerMapper INSTANCE = Mappers.getMapper(OwnerMapper.class);

        PropertyOwner userCreationDtoToPropertyOwner(UserCreationDto userCreationDto);


        @BeforeMapping
        default void checkForNull(UserCreationDto userCreationDto) {
                Optional.ofNullable(userCreationDto).orElseThrow(() -> new IllegalArgumentException("UserCreationDto cannot be null"));
                Optional.ofNullable(userCreationDto.id()).orElseThrow(() -> new IllegalArgumentException("ID cannot be null"));
                Optional.ofNullable(userCreationDto.username()).orElseThrow(() -> new IllegalArgumentException("Username cannot be null"));
                Optional.ofNullable(userCreationDto.email()).orElseThrow(() -> new IllegalArgumentException("Email cannot be null"));
                Optional.ofNullable(userCreationDto.name()).orElseThrow(() -> new IllegalArgumentException("Name cannot be null"));
                Optional.ofNullable(userCreationDto.surname()).orElseThrow(() -> new IllegalArgumentException("Surname cannot be null"));
                Optional.ofNullable(userCreationDto.password()).orElseThrow(() -> new IllegalArgumentException("Password cannot be null"));
                Optional.ofNullable(userCreationDto.phoneNumber()).orElseThrow(() -> new IllegalArgumentException("Phone number cannot be null"));
        }

        @BeforeMapping
        default void checkForInvalid(UserCreationDto userCreationDto) {

                Optional.ofNullable(userCreationDto.phoneNumber())
                        .filter(phoneNumber -> Pattern.matches("^\\+(?:[0-9] ?){6,14}[0-9]$", phoneNumber))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid phone number format"));
                Optional.ofNullable(userCreationDto.email())
                        .filter(email -> Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid email format"));
                Optional.ofNullable(userCreationDto.username())
                        .filter(username -> username.length() >= 5 && username.length() <= 20)
                        .orElseThrow(() -> new IllegalArgumentException("Username must be between 5 and 20 characters long"));
                Optional.ofNullable(userCreationDto.id())
                        .filter(id -> String.valueOf(id).length() >= 9 )
                        .orElseThrow(() -> new IllegalArgumentException("ID must be at least 9 digits long"));
        }


        @Mapping(source = "id", target = "id")
        @Mapping(source = "username", target = "username")
        @Mapping(source = "email", target = "email")
        @Mapping(source = "version", target = "version")
        UserResponseDto userToUserResponseDto(User user);
    }



