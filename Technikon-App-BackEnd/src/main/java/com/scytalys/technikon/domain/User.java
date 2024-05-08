package com.scytalys.technikon.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@MappedSuperclass
@Data
public class User {
    @Id
    @Column(unique = true)
    private long id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    private boolean isActive = true;
    private String address;
    private String phoneNumber;

    @Version
    private long version;

}
