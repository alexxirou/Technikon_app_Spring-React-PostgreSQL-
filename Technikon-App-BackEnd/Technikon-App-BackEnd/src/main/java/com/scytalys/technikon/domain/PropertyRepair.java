package com.scytalys.technikon.domain;

import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class PropertyRepair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateOfRepair;
    private String shortDescription;
    private RepairType repairType;
    private RepairStatus repairStatus;
    private BigDecimal cost;
    private String longDescription;

    @ManyToOne
    private Property property;
    @ManyToOne
    private PropertyOwner propertyOwner;
}