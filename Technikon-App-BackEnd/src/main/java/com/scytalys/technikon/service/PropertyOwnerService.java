package com.scytalys.technikon.service;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.dto.UserCreationDto;


public interface PropertyOwnerService extends UserService {
    PropertyOwner createDBUser(UserCreationDto dto);
    boolean checkUserHasProperties(String tin);
}
