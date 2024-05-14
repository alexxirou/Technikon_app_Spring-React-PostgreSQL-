package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.mapper.AdminMapper;
import com.scytalys.technikon.repository.AdminRepository;
import com.scytalys.technikon.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    @Override
    public void getRegisteredOwners(LocalDate from, LocalDate to) {

    }

    @Override
    public void getRegisteredProperties(PropertyOwner owner, LocalDate from, LocalDate to) {

    }
}
