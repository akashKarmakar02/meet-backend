package com.hexhack.meetbackend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {

    private static final String SECRET_KEY = "7LnPbo9x10nSqERm8mMkH+PbPN3qsg8Z2/04WP334VNdwNNHI6Bm03SpRRz7IG8wdgVI4LE5PSFQ6NgegFevLK3w+/5Ss02iluhbfJbstN/2DohWAh3ue8RAZoo3dLleFkfZASezeMU+mHs4LpF1A39AlRanJTSfrz4axYVzjydVvkiGSRgxJZNI3Lla3kpMNNwXeqMyTZl9j/U9fmfksBGsdP+ccYC+azpQvQow5BSxlW6Icl16ra57cQahx2z4iAhinU2akRozbTmOlimDdYa+jvr+d53ezR2OBldpyjDy7CgjNe49c6W7gSyO+1HMndtrhdp7vUWHYG5oIwVBgWChXw4U5HnZAXo0GvHD+6E=";

    public String extractUserEmail(String token) {
        return null;
    }

    Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKet())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKet() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
