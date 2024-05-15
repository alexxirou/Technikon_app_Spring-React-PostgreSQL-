package com.scytalys.technikon.dto.repair;

import java.time.LocalDate;

public record PropertyRepairSearchByDatesDto(long propertyOwnerId,
                                             LocalDate firstDate,
                                             LocalDate lastDate) {
}
