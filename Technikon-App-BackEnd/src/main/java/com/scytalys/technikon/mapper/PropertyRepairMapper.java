package com.scytalys.technikon.mapper;

import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.dto.repair.PropertyRepairDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PropertyRepairMapper {
    PropertyRepairMapper INSTANCE = Mappers.getMapper(PropertyRepairMapper.class);
    @Mapping(source = "id", target= "id")
    @Mapping(source = "propertyOwnerId", target = "propertyOwner.id")
    @Mapping(source = "propertyId", target = "property.id")
    PropertyRepair RepairDtoToPropertyRepair(PropertyRepairDto propertyRepairDto);

    @Mapping(source = "id", target= "id")
    @Mapping(source = "propertyOwner.id", target = "propertyOwnerId")
    @Mapping(source = "property.id", target = "propertyId")
    PropertyRepairDto RepairToPropertyRepairDto(PropertyRepair propertyRepair);

}