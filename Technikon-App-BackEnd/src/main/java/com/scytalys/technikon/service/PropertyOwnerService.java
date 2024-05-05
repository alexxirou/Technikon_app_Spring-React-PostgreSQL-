package com.scytalys.technikon.service;

import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;


public interface PropertyOwnerService extends UserService {
    PropertyOwner createUser(PropertyOwner user);
    void updateUserAddress(String address, PropertyOwner propertyOwner);
    void softDeleteUser(PropertyOwner propertyOwner);
    void verifyConstraintsEmail(String email);
    void verifyConstraintsId(Long id);
    void verifyConstraintsUsername(String username);
    void verifySearchResult(User user);
}
