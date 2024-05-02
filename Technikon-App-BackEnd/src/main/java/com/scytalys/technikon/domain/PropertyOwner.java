package com.scytalys.technikon.domain;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class PropertyOwner extends User {
    private String address;
    private long phoneNumber;
}