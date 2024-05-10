package com.scytalys.technikon.service;
import com.scytalys.technikon.domain.PropertyOwner;


public interface PropertyOwnerService extends UserService {
    PropertyOwner createDBUser(PropertyOwner user);
    boolean checkUserHasProperties(long userId);
}
