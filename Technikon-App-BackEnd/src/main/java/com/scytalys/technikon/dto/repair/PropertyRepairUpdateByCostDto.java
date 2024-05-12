package com.scytalys.technikon.dto.repair;

import java.math.BigDecimal;

public record PropertyRepairUpdateByCostDto(long id,
                                            long propertyOwnerId,
                                            long propertyId,
                                            BigDecimal cost
                                        ) {
}
