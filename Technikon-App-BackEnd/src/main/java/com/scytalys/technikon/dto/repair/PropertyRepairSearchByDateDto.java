package com.scytalys.technikon.dto.repair;

import java.time.LocalDate;

public record PropertyRepairSearchByDateDto(long propertyOwnerId,
                                      long propertyId,
                                      LocalDate dateOfRepair) {
}
