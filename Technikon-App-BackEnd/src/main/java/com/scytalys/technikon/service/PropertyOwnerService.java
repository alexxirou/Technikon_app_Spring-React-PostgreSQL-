package com.scytalys.technikon.service;

import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserUpdateDto;
import com.scytalys.technikon.service.UserService;

public interface PropertyOwnerService extends UserService {
    PropertyOwner createUser(PropertyOwner user);
    void updateUserAddress(String address, PropertyOwner propertyOwner);
    void softDeleteUser(PropertyOwner propertyOwner);
    UserUpdateDto createUserUpdateResponseDto(long id, String password, String email, String address, String errorMessage );
    void verifyConstraintsEmail(String email);
    void verifyConstraintsId(Long id);
    void verifyConstraintsUsername(String username);
    void verifySearchResult(User user);
}
