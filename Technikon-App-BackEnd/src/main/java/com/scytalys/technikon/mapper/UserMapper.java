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

@Mapper
public interface UserMapper {
        UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

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



        @Mapping(source = "id", target = "id")
        @Mapping(source = "username", target = "username")
        @Mapping(source = "email", target = "email")
        @Mapping(source = "version", target = "version")
        UserResponseDto userToUserResponseDto(User user);
    }



