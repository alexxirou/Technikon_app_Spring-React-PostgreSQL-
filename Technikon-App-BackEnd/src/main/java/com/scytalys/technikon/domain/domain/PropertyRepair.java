package com.scytalys.technikon.domain.domain;

import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class PropertyRepair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
