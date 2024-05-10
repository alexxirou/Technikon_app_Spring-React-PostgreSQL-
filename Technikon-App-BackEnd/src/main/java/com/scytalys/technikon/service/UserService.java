package com.scytalys.technikon.service;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.dto.UserSearchDto;
import com.scytalys.technikon.dto.UserUpdateDto;

public interface UserService {
    //Abstract methods to be used by both types of Users

    int softDeleteUser(String tin, long version);
    public User searchUser(UserSearchDto dto);
    void deleteUser(String tin);
    void UpdateUser(UserUpdateDto dto);
    UserResponseDto createUserResponseDto(User user);


    }
