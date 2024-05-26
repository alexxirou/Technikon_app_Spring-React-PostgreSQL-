package com.scytalys.technikon.dto.user;

import java.util.List;

public record UserDetailsDto(UserSearchResponseDto userInfo, List<String> properties) {
}
