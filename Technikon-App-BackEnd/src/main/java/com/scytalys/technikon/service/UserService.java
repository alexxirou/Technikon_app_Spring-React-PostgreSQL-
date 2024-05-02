package com.scytalys.technikon.service;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.dto.UserResponseToSearchDto;
import com.scytalys.technikon.dto.UserUpdateDto;

public interface UserService {
    //Abstract methods to be used by both types of Users
    public User createUser(User user);
    public User searchUser(long Id);
    public User searchUser(String username);
    public void deleteUser(long UserId);
    public UserResponseDto createUserCreationDto(User user, String errorMessage);
    public UserResponseToSearchDto createUserSearchDto(long id, String username, String email, String errorMessage);



    }
