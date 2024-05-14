package com.scytalys.technikon.service;
import com.scytalys.technikon.dto.*;

public interface UserService<T> {
    //Abstract methods to be used by both types of Users

    void softDeleteUser(String tin);
    void deleteUser(String tin);
    void UpdateUser(UserUpdateDto dto);
    UserResponseDto createUserResponseDto(T user);
    UserSearchResponseDto createSearchResponse(T user);
    T searchUser(UserSearchDto dto);

}
