package com.scytalys.technikon.service;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public interface UserService {
    //Abstract methods to be used by both types of Users

    User searchUserById(long Id);
    User searchUserByUsername(String username);
    User searchUserByEmail(String email);
    void deleteUser(long UserId);
    void updateUserPassword(String password, User user);
    void updateUserEmail(String email, User user);
    UserResponseDto createUserResponseDto(long id, String username, String email);



    }
