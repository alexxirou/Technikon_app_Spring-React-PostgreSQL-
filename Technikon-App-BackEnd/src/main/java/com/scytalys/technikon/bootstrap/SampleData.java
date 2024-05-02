package com.scytalys.technikon.bootstrap;


import com.scytalys.technikon.domain.PropertyRepair;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import com.scytalys.technikon.service.PropertyRepairService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Configuration
@Slf4j
public class SampleData {

    private final PropertyRepairService propertyRepairService;

    @Bean
    public CommandLineRunner myCommandLineRunner(){
        return args -> {
            PropertyRepair propertyRepair = new PropertyRepair();
            propertyRepair.setDateOfRepair(LocalDate.of(2024, 6, 14));
            propertyRepair.setShortDescription("Plumb work");
            propertyRepair.setRepairType(RepairType.PLUMBING);
            propertyRepair.setRepairStatus(RepairStatus.SCHEDULED);
            propertyRepair.setCost(new BigDecimal(150));
            propertyRepair.setLongDescription("Describing with details the work to be done");
            propertyRepairService.createPropertyRepair(propertyRepair);
        };
    }
}
