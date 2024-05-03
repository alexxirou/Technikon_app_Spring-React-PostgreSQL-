package com.scytalys.technikon.service;

import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserUpdateDto;
import com.scytalys.technikon.service.UserService;

public interface PropertyOwnerService extends UserService {

    public void updateUserAddress(UserUpdateDto userUpdateDto);
    public void softDeleteUser(long propertyOwnerId);
    public UserUpdateDto createUpdateUserDto(long id, String password, String email, String address, String errorMessage );


    }
