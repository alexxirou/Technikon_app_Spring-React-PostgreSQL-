package com.scytalys.technikon.security.controller;

import com.scytalys.technikon.domain.PropertyOwner;
import com.scytalys.technikon.dto.UserCreationDto;
import com.scytalys.technikon.dto.UserResponseDto;
import com.scytalys.technikon.security.dto.AuthRequest;
import com.scytalys.technikon.security.service.JwtService;
import com.scytalys.technikon.security.service.UserInfoDetails;
import com.scytalys.technikon.security.service.UserInfoService;
import com.scytalys.technikon.service.PropertyOwnerService;
import com.scytalys.technikon.utility.HeaderUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class UserAuthenticationController {
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
    public ResponseEntity<Map<String, String>> propertyOwnerLogin(@RequestBody AuthRequest authRequest) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));

        if (authentication.isAuthenticated()) {
            UserInfoDetails userDetails = userInfoService.loadUserByUsername(authRequest.username());
            String token = jwtService.generateToken(userDetails);
            HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "User registered successfully.");
            Map<String, String> tokenResponse = new HashMap<>();
            tokenResponse.put("token", token);
            return new ResponseEntity<>( tokenResponse, headers, HttpStatus.OK);
        } else {
            throw new  BadCredentialsException("Invalid credentials");
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<String> adminLogin(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        if (authentication.isAuthenticated()) {
            UserInfoDetails userDetails = userInfoService.loadUserByUsername(authRequest.username());
            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(token);
        } else {
            throw new  BadCredentialsException("Invalid credentials");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpHeaders headers= HeaderUtility.createHeaders("Success-Header", "User logout successful.");

        return new ResponseEntity<>("Logout successful. Please delete the token from your client storage.", headers, HttpStatus.OK);    }
}
