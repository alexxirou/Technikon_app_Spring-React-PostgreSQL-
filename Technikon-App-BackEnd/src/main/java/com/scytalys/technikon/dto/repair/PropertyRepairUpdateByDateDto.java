package com.scytalys.technikon.dto.repair;

import java.time.LocalDate;

public record PropertyRepairUpdateByDateDto(
        long id,
        long propertyOwnerId,
        long propertyId,
        LocalDate dateOfRepair
) {
}
