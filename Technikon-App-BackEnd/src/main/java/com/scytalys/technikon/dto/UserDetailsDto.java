package com.scytalys.technikon.dto;

import java.util.List;

public record UserDetailsDto(UserSearchResponseDto userInfo, List<String> properties) {
}
