package com.hexhack.meetbackend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "7LnPbo9x10nSqERm8mMkH+PbPN3qsg8Z2/04WP334VNdwNNHI6Bm03SpRRz7IG8wdgVI4LE5PSFQ6NgegFevLK3w+/5Ss02iluhbfJbstN/2DohWAh3ue8RAZoo3dLleFkfZASezeMU+mHs4LpF1A39AlRanJTSfrz4axYVzjydVvkiGSRgxJZNI3Lla3kpMNNwXeqMyTZl9j/U9fmfksBGsdP+ccYC+azpQvQow5BSxlW6Icl16ra57cQahx2z4iAhinU2akRozbTmOlimDdYa+jvr+d53ezR2OBldpyjDy7CgjNe49c6W7gSyO+1HMndtrhdp7vUWHYG5oIwVBgWChXw4U5HnZAXo0GvHD+6E=";

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(
            UserDetails userDetails
    ) {
        return generateToken(new HashMap<String, Object>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userEmail = extractUserEmail(token);
        return (userEmail.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);

        return claimResolver.apply(claims);
    }

    Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
