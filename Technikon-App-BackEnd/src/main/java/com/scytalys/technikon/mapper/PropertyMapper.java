package com.scytalys.technikon.mapper;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.dto.property.PropertyCreateDto;
import com.scytalys.technikon.dto.property.PropertyUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PropertyMapper {
    PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);

    @Mapping(source = "propertyOwner.id", target = "propertyOwnerId")
    @Mapping(source = "id", target = "id")
    PropertyCreateDto toPropertyCreateDto(Property property);

    @Mapping(source = "propertyOwnerId", target = "propertyOwner.id")
    @Mapping(source = "id", target = "id")
    Property PropertyCreateDtoToProperty(PropertyCreateDto propertyCreateDto);

    @Mapping(source = "id", target = "id")
    PropertyUpdateDto toPropertyUpdateDto(Property property);
}

