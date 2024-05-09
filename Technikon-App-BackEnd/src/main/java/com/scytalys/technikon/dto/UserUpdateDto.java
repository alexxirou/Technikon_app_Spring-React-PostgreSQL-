package com.scytalys.technikon.dto;

public record UserUpdateDto(Long id, String email, String address, String password, long version) {
}
