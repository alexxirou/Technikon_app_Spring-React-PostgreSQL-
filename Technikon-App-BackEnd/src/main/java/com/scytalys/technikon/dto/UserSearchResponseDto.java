package com.scytalys.technikon.dto;


import java.util.List;

public record UserSearchResponseDto(String tin,
                                    String username,
                                    String email,
                                    String name,
                                    String surname,
                                    String address,
                                    String phoneNumber,
                                    Boolean isActive,
                                    List<String> properties,
                                    long version) {
}
