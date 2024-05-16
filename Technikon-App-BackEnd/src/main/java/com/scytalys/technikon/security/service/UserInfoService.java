package com.scytalys.technikon.security.service;


import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.dto.UserCreationDto;
import com.scytalys.technikon.mapper.OwnerMapper;
import com.scytalys.technikon.repository.PropertyOwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserInfoService implements UserDetailsService {
    private final PropertyOwnerRepository propertyOwnerRepository;
    //private final AdminRepository adminRepository;



    @Override
    public UserInfoDetails loadUserByUsername(String tin)  {
        PropertyOwner user = propertyOwnerRepository.findByTin(tin).orElseThrow(() ->new IllegalArgumentException("Username does not exist."));
        Optional<UserInfoDetails> result = Optional.of(new UserInfoDetails(user));
        return result.orElseThrow(() -> new BadCredentialsException("User not found " + tin));
    }


    /**
     * Creates a new user in the repository as a PropertyOwner type
     *
     * @param dto The dto containing information of the user to be created.
     * @return ResponseDto.
     * throws IllegalArgumentException if important fields are null;
     * throws DataIntegrityViolationException if unique field constraint violation
     */
    @CacheEvict(value = "PropertyOwners", allEntries = true)
    @Transactional
    public PropertyOwner createDBUser(UserCreationDto dto) {
        PropertyOwner newDbUser = OwnerMapper.INSTANCE.userCreationDtoToPropertyOwner(dto);
        newDbUser.setEmail(newDbUser.getEmail().toLowerCase());
        try {
            propertyOwnerRepository.save(newDbUser);
            return newDbUser;
        }catch (Exception e){
            throw new DataIntegrityViolationException("Id, Username, or Email already taken.");
        }
    }
}