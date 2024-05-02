package com.scytalys.technikon.dto;
//DTO that returns TIN Username and Email of the searched user or 0 and Blank fields and an optional error message
public record UserResponseToSearchDto(long userTin,
                                      String username,
                                      String email,
                                      String errorMessage) {
}
