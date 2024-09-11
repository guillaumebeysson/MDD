package com.openclassrooms.back.services;


import com.openclassrooms.back.services.interfaces.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;


@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private int expiration; // 1 jour d'expiration

    /**
     * Génère un token JWT.
     * @param username le nom d'utilisateur
     * @return le token JWT
     */
    @Override
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }

    /**
     * Extrait le nom d'utilisateur du token.
     * @param token le token
     * @return le nom d'utilisateur
     */
    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrait une revendication du token.
     * @param token le token
     * @param claimsResolver la fonction de résolution des revendications
     * @param <T> le type de la revendication
     * @return la revendication
     */
    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrait les revendications du token.
     * @param token le token
     * @return les revendications
     */
    @Override
    public Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token has expired", e);
        } catch (UnsupportedJwtException e) {
            throw new JwtException("Token format is not supported", e);
        } catch (MalformedJwtException e) {
            throw new JwtException("Token is malformed", e);
        } catch (IllegalArgumentException e) {
            throw new JwtException("Token is null or empty", e);
        } catch (io.jsonwebtoken.security.SecurityException e) {
            throw new JwtException("Invalid token signature", e);
        }
    }

    /**
     * Récupère la clé secrète pour signer le token.
     * @return la clé secrète
     */
    @Override
    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Vérifie si le token est valide.
     * @param token le token
     * @param userDetails les détails de l'utilisateur
     * @return true si le token est valide, false sinon
     */
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Vérifie si le token est expiré.
     * @param token le token
     * @return true si le token est expiré, false sinon
     */
    @Override
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    /**
     * Extrait la date d'expiration du token.
     * @param token le token
     * @return la date d'expiration
     */
    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
