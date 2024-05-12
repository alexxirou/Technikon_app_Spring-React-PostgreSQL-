package com.scytalys.technikon.dto.repair;


public record PropertyRepairUpdateByShortDescriptionDto(
        long id,
        long propertyOwnerId,
        long propertyId,
        String shortDescription
) {
}
