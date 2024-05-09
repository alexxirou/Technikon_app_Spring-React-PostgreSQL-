package com.scytalys.technikon.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@MappedSuperclass
@Data
@NoArgsConstructor
public class User {
    @Id
    @Column(unique = true)
    @NonNull
    private Long id;
    private String name;
    private String surname;
    @Column(unique = true)
    @NonNull
    private String email;
    @NonNull
    @Column(unique = true)
    private String username;
    @NonNull
    private String password;
    private boolean isActive = true;
    private String address;
    private String phoneNumber;
    @Version
    private long version;

}
