package com.scytalys.technikon.dto.repair;

import java.time.LocalDate;

public record PropertyRepairSearchDto(
        LocalDate date,
        LocalDate firstDate,
        LocalDate lastDate
) {
}
