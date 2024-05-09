package com.scytalys.technikon.service;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.dto.UserResponseDto;




public interface PropertyOwnerService extends UserService {
    UserResponseDto createDBUser(PropertyOwner user);
    boolean checkUserHasProperties(long userId);
}
