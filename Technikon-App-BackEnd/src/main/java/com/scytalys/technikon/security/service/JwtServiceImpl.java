package com.scytalys.technikon.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String SECRET;
    @Value("${jwt.minutes}")
    private long MINUTES;

    @Override
    public String generateToken(UserInfoDetails userInfoDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userInfoDetails.getId());
        claims.put("username", userInfoDetails.getUsername());
        claims.put("tin", userInfoDetails.getTin());
        claims.put("authorities", userInfoDetails.getAuthorities());
        return createToken(claims);
    }


    @Override
    public String extractTin(String token) {
        return extractClaim(token, claims -> claims.get("tin", String.class));
    }

    @Override
    public long extractId(String token) {
        return extractClaim(token, claims -> claims.get("id", Long.class));
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.get("username", String.class));
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Key getSignKey() {
        byte[] secretBytes = SECRET.getBytes();
        return Keys.hmacShaKeyFor(secretBytes);
    }

    public String createToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + (MINUTES * 60 * 1000));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
