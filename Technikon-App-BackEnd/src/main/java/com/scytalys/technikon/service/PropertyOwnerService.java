package com.scytalys.technikon.service;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;
import com.scytalys.technikon.dto.UserCreationDto;
import com.scytalys.technikon.dto.UserSearchDto;


public interface PropertyOwnerService extends UserService<PropertyOwner> {

    boolean checkUserHasProperties(String tin);

}
