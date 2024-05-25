package com.scytalys.technikon.dto.property;

import com.scytalys.technikon.domain.category.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyCreateDto {
    private long id;
    private long propertyOwnerId;
    private String tin;
    private String address;
    private LocalDate constructionYear;
    private double latitude;
    private double longitude;
    private PropertyType propertyType;
    private String picture;
}