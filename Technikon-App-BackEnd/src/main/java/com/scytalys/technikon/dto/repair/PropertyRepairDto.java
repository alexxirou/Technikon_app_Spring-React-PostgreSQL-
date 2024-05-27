package com.scytalys.technikon.dto.repair;



import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;

import java.math.BigDecimal;
import java.time.LocalDate;
public record PropertyRepairDto(
        long id,
        long propertyOwnerId,
        long propertyId,
        LocalDate dateOfRepair,
        String shortDescription,
        RepairType repairType,
        RepairStatus repairStatus,
        BigDecimal cost,
        String longDescription,
        String address,
        String propertyTin) {
}

