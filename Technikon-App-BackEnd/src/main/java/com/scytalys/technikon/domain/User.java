package com.scytalys.technikon.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@MappedSuperclass
@Data
public abstract class User {
    @Id
    @Column(unique = true)
    private long id;
    private String name;
    private String surname;
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    private Boolean isActive = true;

}
