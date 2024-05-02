package com.scytalys.technikon.dto;
//DTO that returns the updatable fields the id if success and an optional error message on failure
public record UserUpdateDto(long id, String address, String email, String password, String errorMessage) {
}
