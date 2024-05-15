package com.scytalys.technikon.service;
import com.scytalys.technikon.dto.*;

import java.util.List;

public interface UserService<T> {
    //Abstract methods to be used by both types of Users

    void softDeleteUser(String tin);
    void deleteUser(String tin);
    void UpdateUser(String tin, UserUpdateDto dto);
    UserResponseDto createUserResponseDto(T user);
    List<UserSearchResponseDto> createSearchResponse(List<T> users);
    List<T> searchUser(UserSearchDto dto);
    T createDBUser(UserCreationDto dto);
}
