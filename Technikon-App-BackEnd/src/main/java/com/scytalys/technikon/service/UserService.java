package com.scytalys.technikon.service;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.dto.user.*;

import java.util.List;

public interface UserService<T> {
    //Abstract methods to be used by both types of Users

    void softDeleteUser(long id);
    void deleteUser(long id);
    void updateUser(long id, UserUpdateDto dto);
    UserResponseDto createUserResponseDto(T user);
    List<UserSearchResponseDto> createSearchResponse(List<T> users);
    List<T> searchUser(UserSearchDto dto);
    T findUser(String tin);
    T findUser(long id);
    UserDetailsDto userDetails(T user);
}
