package com.scytalys.technikon.domain;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity

public class PropertyOwner extends User {
    private String address;
    private long phoneNumber;




}