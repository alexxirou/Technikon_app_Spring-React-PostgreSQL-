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
        @Named("throwExceptionIfNull")
        default String throwExceptionIfNull(String field) {
                if (field == null) {
                        throw new IllegalArgumentException("Field cannot be null");
                }
                return field;
        }

        @Named("throwExceptionIfNull")
        default Long throwExceptionIfNull(Long id) {
                if (id == null) {
                        throw new IllegalArgumentException("ID cannot be null");
                }
                return id;
        }

        @Mapping(target = "id", source = "id", qualifiedByName = "throwExceptionIfNull")
        @Mapping(target = "username", source = "username", qualifiedByName = "throwExceptionIfNull")
        @Mapping(target = "email", source = "email", qualifiedByName = "throwExceptionIfNull")
        @Mapping(target = "password", source = "password", qualifiedByName = "throwExceptionIfNull")
        @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "throwExceptionIfNull")
        @Mapping(target = "name", source = "name", qualifiedByName = "throwExceptionIfNull")
        @Mapping(target = "surname", source = "surname", qualifiedByName = "throwExceptionIfNull")
        @Mapping(source = "address", target = "address", qualifiedByName = "throwExceptionIfNull")
        PropertyOwner userCreationDtoToOwner(UserCreationDto userCreationDto);



        @Mapping(source = "id", target = "id")
        @Mapping(source = "username", target = "username")
        @Mapping(source = "email", target = "email")
        @Mapping(source = "version", target = "version")
        UserResponseDto userToUserResponseDto(User user);


        @Mapping(source = "email", target = "email", defaultValue = MappingConstants.NULL)
        @Mapping(source = "address", target = "address", defaultValue = MappingConstants.NULL)
        @Mapping(source = "password", target = "password", defaultValue = MappingConstants.NULL)
        @Mapping(source = "version", target = "version")
        PropertyOwner userUpdateDtoToOwner(UserUpdateDto userUpdateDto, @Context User user);

        @Mapping(source = "email", target = "email", defaultValue = MappingConstants.NULL)
        @Mapping(source = "address", target = "address", defaultValue = MappingConstants.NULL)
        @Mapping(source = "password", target = "password", defaultValue = MappingConstants.NULL)
        @Mapping(source = "version", target = "version")
        Admin userUpdateDtoToAdmin(UserUpdateDto userUpdateDto, @Context User user);

        @Mapping(target = "id", source = "id", qualifiedByName = "throwExceptionIfNull")
        @Mapping(target = "username", source = "username", qualifiedByName = "throwExceptionIfNull")
        @Mapping(target = "email", source = "email", qualifiedByName = "throwExceptionIfNull")
        @Mapping(target = "password", source = "password", qualifiedByName = "throwExceptionIfNull")
        @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "throwExceptionIfNull")
        @Mapping(target = "name", source = "name", qualifiedByName = "throwExceptionIfNull")
        @Mapping(target = "surname", source = "surname", qualifiedByName = "throwExceptionIfNull")
        @Mapping(source = "address", target = "address", qualifiedByName = "throwExceptionIfNull")
        Admin userCreationDtoToAdmin(UserCreationDto userCreationDto);
    }



