package com.scytalys.technikon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scytalys.technikon.domain.category.PropertyType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    @NonNull
    private String tin;
    private String address;
    private LocalDate constructionYear;
    private PropertyType propertyType;
    private double latitude;
    private double longitude;
    private String picture;
    @ManyToOne
    private PropertyOwner propertyOwner;
}







