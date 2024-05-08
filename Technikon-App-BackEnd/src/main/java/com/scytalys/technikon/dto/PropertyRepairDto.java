package com.scytalys.technikon.dto;

import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public record PropertyRepairDto(
        Long propertyOwnerId,
        Long propertyId,
        LocalDate dateOfRepair,
        String shortDescription,
        RepairType repairType,
        RepairStatus repairStatus,
        BigDecimal cost,
        String longDescription) {
}

