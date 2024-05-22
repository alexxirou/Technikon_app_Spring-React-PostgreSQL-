package com.scytalys.technikon.domain;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;



import java.time.LocalDateTime;


@MappedSuperclass
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(unique = true)
    private String tin;
    @NonNull
    private String name;
    @NonNull
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
    @NonNull
    private String phoneNumber;
    @Version
    private long version;
    private LocalDateTime registrationDate= LocalDateTime.now();


}
