package com.scytalys.technikon.service;

import com.scytalys.technikon.domain.Admin;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import java.time.LocalDate;
import java.util.List;

public interface AdminService extends UserService<Admin>{
    List<PropertyOwner> getRegisteredOwners(LocalDate from, LocalDate to);
    List<Property> getRegisteredProperties(PropertyOwner owner, LocalDate from, LocalDate to);

}
