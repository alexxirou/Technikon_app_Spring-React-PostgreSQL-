package com.scytalys.technikon.dto.repair;

import com.scytalys.technikon.domain.category.RepairStatus;

public record PropertyRepairDeleteDto(
        long propertyOwnerId,
        RepairStatus repairStatus
) {
}
