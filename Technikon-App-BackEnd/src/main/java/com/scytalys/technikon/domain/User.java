package com.scytalys.technikon.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@MappedSuperclass
@Data
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    private String address;
    private long phoneNumber;
}
