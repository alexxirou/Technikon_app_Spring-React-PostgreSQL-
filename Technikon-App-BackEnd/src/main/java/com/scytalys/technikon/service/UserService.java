package com.scytalys.technikon.service;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserResponseDto;

public interface UserService {
    //Abstract methods to be used by both types of Users

    User searchUserById(long Id);
    User searchUserByUsername(String username);
    User searchUserByEmail(String email);
    void deleteUser(long UserId);
    User updateUserPassword(long id,String password, long version);
    User updateUserEmail(long id, String email, long version);
    UserResponseDto createUserResponseDto(long id, String username, String email, long version);



    }
