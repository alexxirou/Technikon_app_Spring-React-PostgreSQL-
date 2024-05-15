package com.scytalys.technikon.dto;

import java.util.List;

public record UserDetails(UserSearchResponseDto userInfo, List<String> properties) {
}
