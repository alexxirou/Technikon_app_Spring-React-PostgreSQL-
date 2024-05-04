package com.scytalys.technikon.service;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.dto.UserResponseToSearchDto;
import com.scytalys.technikon.dto.UserUpdateDto;

public interface UserService {
    //Abstract methods to be used by both types of Users

    User searchUser(long Id);
    User searchUser(String username);
    void deleteUser(long UserId);
    void updateUserPassword(String password, User user);
    void updateUserEmail(String email, User user);
    UserResponseDto createUserCreationResponseDto(User user, String errorMessage);
    UserResponseToSearchDto createUserSearchResponseDto(long id, String username, String email, String errorMessage);



    }
