package com.scytalys.technikon.service;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.dto.UserUpdateDto;

public interface UserService {
    //Abstract methods to be used by both types of Users

    int softDeleteUser(long id, long version);
    public User searchUser(User user);
    void deleteUser(long UserId);
    void UpdateUser(UserUpdateDto dto);
    UserResponseDto createUserResponseDto(User user);


    }
