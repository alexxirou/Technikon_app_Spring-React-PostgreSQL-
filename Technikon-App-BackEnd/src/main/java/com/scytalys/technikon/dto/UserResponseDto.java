package com.scytalys.technikon.dto;

import com.scytalys.technikon.domain.User;
//Returns the User object or Null and an optional message in case of failure
public record UserResponseDto(User user,
                              String errorMessage) {
}
