package com.api.gymapi.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {


    @Value("${jwt.secret}")
    private String secretKey;

    private final long expirationTime = 86400000L;


    /**
     * Générer un token JWT pour l'utilisateur
     * Les employés auront un rôle dans le token, mais pas les clients
     */

    public String generateToken(String username, String role) {
        if (role == null || role.isEmpty()) {
            // Pour les clients, on ne met pas de rôle dans le token
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
        } else {
            // Pour les employés, on ajoute le rôle dans le token
            return Jwts.builder()
                    .setSubject(username)
                    .claim("role", role)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
        }
    }

    /**
     * Valider le token JWT et obtenir les informations qu'il contient
     */

    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        return validateToken(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return (String) validateToken(token).get("role");
    }

    public boolean isTokenExpired(String token) {
        return validateToken(token).getExpiration().before(new Date());
    }
}
