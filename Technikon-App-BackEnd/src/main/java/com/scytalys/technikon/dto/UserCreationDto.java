package com.scytalys.technikon.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Version;

public record UserCreationDto(String tin,
                              String name,
                              String surname,
                              String email,
                              String username,
                              String password,
                              String address,
                              String phoneNumber,
                              long version) {
}
