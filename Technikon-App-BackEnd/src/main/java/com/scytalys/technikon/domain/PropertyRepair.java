package com.scytalys.technikon.domain;

import com.scytalys.technikon.domain.category.RepairStatus;
import com.scytalys.technikon.domain.category.RepairType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
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
    @NonNull
    private Property property;
    @ManyToOne
    @NonNull
    private PropertyOwner propertyOwner;
}
