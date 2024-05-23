package com.scytalys.technikon.dto;
//Update
import com.scytalys.technikon.domain.category.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyUpdateDto {
    private LocalDate constructionYear;
    private String address;
    private double latitude;
    private double longitude;
    private String picture;
    private PropertyType propertyType;
}

