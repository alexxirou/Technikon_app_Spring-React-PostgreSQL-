package com.scytalys.technikon.service;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserResponseDto;

public interface UserService {
    //Abstract methods to be used by both types of Users

    int updateUserAddress(long id, String address, long version);
    int softDeleteUser(long id, long version);
    void verifyConstraintsEmail(String email);
    void verifyConstraintsId(Long id);
    void verifyConstraintsUsername(String username);
    void verifySearchResult(User user);
    User searchUserById(long Id);
    User searchUserByUsername(String username);
    User searchUserByEmail(String email);
    void deleteUser(long UserId);
    int updateUserPassword(long id,String password, long version);
    int updateUserEmail(long id, String email, long version);
    UserResponseDto createUserResponseDto(long id, String username, String email, long version);



    }
