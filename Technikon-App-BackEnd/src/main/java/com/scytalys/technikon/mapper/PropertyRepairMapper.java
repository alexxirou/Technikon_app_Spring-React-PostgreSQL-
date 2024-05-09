package com.scytalys.technikon.mapper;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.dto.PropertyRepairCreationDto;
import com.scytalys.technikon.dto.PropertyRepairDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PropertyRepairMapper {
    PropertyRepairMapper INSTANCE = Mappers.getMapper(PropertyRepairMapper.class);

    @Mapping(source = "propertyOwnerId", target = "propertyOwner.id")
    @Mapping(source = "propertyId", target = "property.id")
    PropertyRepair convertPropertyRepairCreationDtoToPropertyRepair(PropertyRepairCreationDto propertyRepairCreationDto);

    @Mapping(source = "propertyOwner.id", target = "propertyOwnerId")
    @Mapping(source = "property.id", target = "propertyId")
    PropertyRepairCreationDto convertPropertyRepairToPropertyRepairCreationDto(PropertyRepair propertyRepair);

    @Mapping(source = "propertyOwnerId", target = "propertyOwner.id")
    @Mapping(source = "propertyId", target = "property.id")
    PropertyRepair convertPropertyRepairDtoToPropertyRepair(PropertyRepairDto propertyRepairDto);

    @Mapping(source = "propertyOwner.id", target = "propertyOwnerId")
    @Mapping(source = "property.id", target = "propertyId")
    PropertyRepairDto convertPropertyRepairToPropertyRepairDto(PropertyRepair propertyRepair);
}

//    PropertyRepairMapper INSTANCE = Mappers.getMapper(PropertyRepairMapper.class);
//
//    @Mapping(source = "propertyOwnerId", target = "propertyOwner.id")
//    @Mapping(source = "propertyId", target = "property.id")
//    PropertyRepair convertPropertyRepairCreationDtoToPropertyRepair(PropertyRepairCreationDto propertyRepairCreationDto);
//    @Mapping(source = "propertyOwner.id", target = "propertyOwnerId")
//    @Mapping(source = "property.id", target = "propertyId")
//    PropertyRepairCreationDto convertPropertyRepairToPropertyRepairCreationDto(PropertyRepair propertyRepair);
//    @Mapping(source = "propertyOwnerId", target = "propertyOwner.id")
//    @Mapping(source = "propertyId", target = "property.id")
//    PropertyRepair propertyRepairDtoToPropertyRepair(PropertyRepairDto propertyRepairDto);
//    @Mapping(source = "propertyOwner.id", target = "propertyOwnerId")
//    @Mapping(source = "property.id", target = "propertyId")
//    PropertyRepairDto propertyRepairToPropertyRepairDto(PropertyRepair propertyRepair);


//@Mapper
//public interface MyMapper {
//    @Mapping(target = "myField", expression = "java(mapOptional(source.getMyField(), destination.getMyField()))")
//    MyClass map(Source source, MyClass destination);
//    default String mapOptional(Optional<String> newStr, String originalStr) {
//        return newStr.filter(str -> !str.isEmpty()).orElse(originalStr);
//    }
//}

//@Mapper(componentModel = "spring")
//public interface PropertyRepairMapper {
//
//    PropertyRepairDto convertPropertyRepairToDto(PropertyRepair propertyRepair);
//    PropertyRepair convertPropertyRepairDtoToPropertyRepair(PropertyRepairDto propertyRepairDto);
//    PropertyRepair convertPropertyRepairCreationDtoToPropertyRepair(PropertyRepairCreationDto propertyRepairCreationDto);
//    PropertyRepairCreationDto convertPropertyRepairToPropertyRepairCreationDto(PropertyRepair propertyRepair);
//
//}

//
