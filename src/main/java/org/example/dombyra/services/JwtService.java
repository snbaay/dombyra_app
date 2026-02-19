package org.example.dombyra.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    public static final String SECRET_KEY ="5ed06ee52d0e76abf5ade42135dd0d7546ffc60056db6a364c4f447fafccd45d";
    public String extractUserPhoneNumber(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String userPhoneNumber = extractUserPhoneNumber(token);
        return (userPhoneNumber.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    public boolean isRefreshTokenValid(String token,String phoneNumber){
        final String userPhoneNumber = extractUserPhoneNumber(token);
        return (userPhoneNumber.equals(phoneNumber)) && !isTokenExpired(token);
    }
    public String generateAccessToken(String phoneNumber){
        return generateToken(new HashMap<>(),phoneNumber,1);
    }
    public String generateRefreshToken(String phoneNumber){
        return generateToken(new HashMap<>(),phoneNumber,7);
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
  
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private String generateToken(Map<String,Object> extraClaims, String phoneNumber,int expirationDays){
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(phoneNumber)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (long) 60 * 60 * 24 * 1000 * expirationDays))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
