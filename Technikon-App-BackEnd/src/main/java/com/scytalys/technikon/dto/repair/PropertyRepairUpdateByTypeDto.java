package com.scytalys.technikon.dto.repair;

import com.scytalys.technikon.domain.category.RepairType;

public record PropertyRepairUpdateByTypeDto(long id,
                                            long propertyOwnerId,
                                            long propertyId,
                                            RepairType repairType) {
}
