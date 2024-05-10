package com.scytalys.technikon.dto;
//DTO that returns TIN Username and Email of the searched user or 0 and Blank fields and an optional error message
public record UserResponseDto(String tin,
                              String username,
                              String email, long version) {
}
