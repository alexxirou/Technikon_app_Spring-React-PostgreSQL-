package com.scytalys.technikon.dto;


import java.util.List;
public record UserSearchResponseDto(Long id,
                                    String tin,
                                    String username,
                                    String email,
                                    String name,
                                    String surname,
                                    String address,
                                    String phoneNumber,
                                    boolean isActive
) {
}

