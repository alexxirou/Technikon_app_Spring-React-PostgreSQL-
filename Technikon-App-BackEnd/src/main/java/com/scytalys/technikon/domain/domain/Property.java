package com.scytalys.technikon.domain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.category.PropertyType;
import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Property {
    @Id
    @Column(unique = true)
    private long id;
    private String address;
    private LocalDate constructionYear;
    private PropertyType propertyType;
    private double latitude;
    private double longitude;
    private String picture;
    @ManyToOne
    @NotNull
    private PropertyOwner propertyOwner;
}







