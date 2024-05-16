package com.scytalys.technikon.service;
import com.scytalys.technikon.dto.*;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService<T> {
    //Abstract methods to be used by both types of Users

    void softDeleteUser(String tin, Authentication authentication);
    void deleteUser(String tin, Authentication authentication);
    void updateUser(String tin, UserUpdateDto dto, Authentication authentication);
    UserResponseDto createUserResponseDto(T user);
    List<UserSearchResponseDto> createSearchResponse(List<T> users);
    List<T> searchUser(UserSearchDto dto);
    T findUser(String tin);
    UserDetailsDto userDetails(T user, Authentication authentication);
}
