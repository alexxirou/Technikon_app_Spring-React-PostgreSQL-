package com.scytalys.technikon.dto.repair;

import java.time.LocalDate;

public record PropertyRepairSearchByDatesDto(long propertyOwnerId,
                                             long propertyId,
                                             LocalDate firstDate,
                                             LocalDate lastDate) {
}
