package com.scytalys.technikon.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString(callSuper = true)

public class PropertyOwner extends User {

    private String address;
    private String phoneNumber;



}