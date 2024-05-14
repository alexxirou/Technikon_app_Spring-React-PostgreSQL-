package com.scytalys.technikon.service;

import com.scytalys.technikon.domain.PropertyOwner;
import java.time.LocalDate;

public interface AdminService {
    void getRegisteredOwners(LocalDate from, LocalDate to);
    void getRegisteredProperties(PropertyOwner owner, LocalDate from, LocalDate to);
}
