package com.scytalys.technikon.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;


public interface PropertyOwnerService extends UserService {
    PropertyOwner createUser(PropertyOwner user);
    PropertyOwner updateUserAddress(long id, String address, long version);
    PropertyOwner softDeleteUser(long id, long version);
    void verifyConstraintsEmail(String email);
    void verifyConstraintsId(Long id);
    void verifyConstraintsUsername(String username);
    void verifySearchResult(User user);
    boolean checkUserProperties(long id);
}
