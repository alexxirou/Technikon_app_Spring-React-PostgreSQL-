package com.scytalys.technikon.service;


import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.domain.User;

import java.util.ArrayList;
import java.util.List;


public interface PropertyOwnerService extends UserService {
    PropertyOwner createDBUser(PropertyOwner user);
    ArrayList<Long> findPropertiesForUser(long userId);
    boolean checkUserHasProperties(List<Long> propertyIds);
}
