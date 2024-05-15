package com.scytalys.technikon.dto;

public record UserSearchResponseDto(String tin,
                                    String username,
                                    String email,
                                    String name,
                                    String surname,
                                    String address,
                                    String phoneNumber,
                                    boolean isActive
) {
}

