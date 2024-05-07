package com.scytalys.technikon.service;


import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;

import java.util.ArrayList;
import java.util.List;


public interface PropertyOwnerService extends UserService {
    PropertyOwner createUser(PropertyOwner user);
    int updateUserAddress(long id, String address, long version);
    int softDeleteUser(long id, long version);
    void verifyConstraintsEmail(String email);
    void verifyConstraintsId(Long id);
    void verifyConstraintsUsername(String username);
    void verifySearchResult(User user);
    ArrayList<Long> findPropertiesForUser(long userId);
    boolean checkUserHasProperties(List<Long> propertyIds);
}
