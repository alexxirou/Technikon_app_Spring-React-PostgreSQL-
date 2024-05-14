package com.scytalys.technikon.dto;

public record UserUpdateDto(String tin, String email, String address, String password, long version) {
}
