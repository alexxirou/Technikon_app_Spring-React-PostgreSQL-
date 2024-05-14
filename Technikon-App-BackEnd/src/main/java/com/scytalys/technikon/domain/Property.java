package com.scytalys.technikon.domain;
import com.scytalys.technikon.domain.category.PropertyType;
import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

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







