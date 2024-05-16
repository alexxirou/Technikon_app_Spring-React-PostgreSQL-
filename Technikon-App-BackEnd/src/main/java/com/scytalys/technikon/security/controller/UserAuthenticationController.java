package com.scytalys.technikon.security.controller;

import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.dto.UserCreationDto;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.security.dto.AuthRequest;
import com.scytalys.technikon.security.service.JwtService;
import com.scytalys.technikon.security.service.UserInfoService;
import com.scytalys.technikon.service.PropertyOwnerService;
import com.scytalys.technikon.utility.HeaderUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class UserAuthenticationController {
    private final UserInfoService service;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserInfoService userInfoService;
    private final PropertyOwnerService propertyOwnerService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createPropertyOwner(@RequestBody UserCreationDto newUser) {
        PropertyOwner newDBUser=userInfoService.createDBUser(newUser);
        UserResponseDto userInfo = propertyOwnerService.createUserResponseDto(newDBUser);
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "User registered successfully.");
        String loginUrl = ServletUriComponentsBuilder.fromPath("/auth")
                .replacePath("/login")
                .build()
                .toUriString();
        headers.add("Location", loginUrl);
        return new ResponseEntity<>( userInfo, headers, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody com.scytalys.technikon.security.dto.AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.tin(), authRequest.password()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken( authRequest.tin());
            return ResponseEntity.ok(token);
        } else throw new BadCredentialsException("Invalid credentials");

    }

    @PostMapping("/admin/login")
    public ResponseEntity<String> adminLogin(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.tin(), authRequest.password()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.tin());
            return ResponseEntity.ok(token);
        } else throw new BadCredentialsException("Invalid credentials");

    }

}
