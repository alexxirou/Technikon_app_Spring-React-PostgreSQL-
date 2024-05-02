package com.scytalys.technikon.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PropertyRepairDto (long propertyOwnerId, LocalDate date){
}
