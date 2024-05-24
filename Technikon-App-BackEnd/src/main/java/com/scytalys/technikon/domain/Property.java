package com.scytalys.technikon.domain;
import com.scytalys.technikon.domain.category.PropertyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Entity(name = "Property")
@Data
@NoArgsConstructor
public class Property {
    @Id
    @Column(name = "id",nullable = false, length = 25)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(name = "tin" , nullable = false, length = 11 )
    private String tin;
    @Column(name = "Address", nullable = false, length = 50)
    private String address;
    @Column(name = "ConstructionYear", nullable = false, length = 4)
    private LocalDate constructionYear;
    @Column(name = "PropertyType" , nullable = false, length = 20)
    private PropertyType propertyType;
    @Column(name= "Latitude" , nullable= false , length = 8)
    private double latitude;
    @Column(name= "Longitude" , nullable= false , length = 8)
    private double longitude;
    @Column(name= "Picture" )
    private String picture;
    private boolean isActive = true;
    @ManyToOne
    @NonNull
    @JoinColumn(name = "Owner", referencedColumnName = "id")
    private PropertyOwner propertyOwner;
}







