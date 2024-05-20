package com.scytalys.technikon.dto;

public record UserCreationDto(String tin,
                              String name,
                              String surname,
                              String email,
                              String username,
                              String password,
                              String address,
                              String phoneNumber) {
}
