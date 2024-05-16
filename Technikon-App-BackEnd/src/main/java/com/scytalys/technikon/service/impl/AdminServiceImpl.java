package com.scytalys.technikon.service.impl;

import com.scytalys.technikon.domain.Admin;
import com.scytalys.technikon.domain.Property;
import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.dto.*;
import com.scytalys.technikon.mapper.AdminMapper;
import com.scytalys.technikon.repository.AdminRepository;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import com.scytalys.technikon.repository.PropertyRepository;
import com.scytalys.technikon.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final PropertyOwnerRepository propertyOwnerRepository;
    private final PropertyRepository propertyRepository;
    private final AdminMapper adminMapper;

    @Override
    public void softDeleteUser(String tin) {

    }

    @Override
    public void deleteUser(String tin) {

    }

    @Override
    public void UpdateUser(String tin, UserUpdateDto dto) {

    }

    @Override
    public UserResponseDto createUserResponseDto(Admin user) {
        return null;
    }

    @Override
    public List<UserSearchResponseDto> createSearchResponse(List<Admin> users) {
        return List.of();
    }

    @Override
    public List<Admin> searchUser(UserSearchDto dto) {
        return List.of();
    }

    @Override
    public Admin createDBUser(UserCreationDto dto) {
        return null;
    }

    @Override
    public Admin findUser(String tin) {
        return null;
    }

    @Override
    public UserDetails userDetails(Admin user) {
        return null;
    }

    @Override
    public List<PropertyOwner> getRegisteredOwners(LocalDate from, LocalDate to) {
        return propertyOwnerRepository.findAll();
    }

    @Override
    public List<Property> getRegisteredProperties(PropertyOwner owner, LocalDate from, LocalDate to) {
       return propertyRepository.findAll();
    }
}
