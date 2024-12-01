package com.eureka.testeeureka.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class JwtService {

    private final String secretKey = "d1kLjs8S-zX1QG7TQfYWxVYLTZVxKXVY9eWzM3tRVn4Pg4xQxnTwJQF-R1KyZ3N7XSXT3zV-XXpWJfWFhVJG7LTk7P";
    private final long jwtExpirationMs = 3600000; // 1 hora

    // Gera um token JWT
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Valida o token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extrai o email do token
    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Recupera as roles (autoridades) do token
    public List<String> getAuthoritiesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // Aqui estou assumindo que você está armazenando as roles como uma string simples.
        // Caso esteja usando uma lista, ajuste para mapear corretamente.
        return List.of(claims.get("role").toString());
    }

    // Extrai uma informação específica do token
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}
