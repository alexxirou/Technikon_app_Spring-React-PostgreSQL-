package com.scytalys.technikon.security.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
public record AuthRequest( String username,  String password){}
