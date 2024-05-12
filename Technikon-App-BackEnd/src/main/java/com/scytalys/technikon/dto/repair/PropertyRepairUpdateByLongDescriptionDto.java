package com.scytalys.technikon.dto.repair;

public record PropertyRepairUpdateByLongDescriptionDto(
        long id,
        long propertyOwnerId,
        long propertyId,
        String longDescription
) {
}
