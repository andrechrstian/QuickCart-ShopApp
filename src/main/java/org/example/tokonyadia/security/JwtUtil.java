package org.example.tokonyadia.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.annotation.PostConstruct;
import org.example.tokonyadia.entity.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${app.tokonyadiashop.jwt.jwt-secret}")
    private String jwtSecret;
    @Value("${app.tokonyadiashop.jwt.app-name}")
    private String appName;
    @Value("${app.tokonyadiashop.jwt.jwt-expired}")
    private long jwtExpired;

    private Algorithm algorithm;

    @PostConstruct
    private void initAlgorithm() {
        this.algorithm =Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(AppUser appUser) {
        return JWT.create()
                //Set payload
                .withIssuer(appName)
                .withSubject(appUser.getId())
                .withExpiresAt(Instant.now().plusSeconds(jwtExpired))
                .withIssuedAt(Instant.now())
                .withClaim("role", appUser.getRole().name())
                .sign(algorithm);

    }

    public boolean verifyJwtToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodeJWT = verifier.verify(token);
        return decodeJWT.getIssuer().equals(appName);

    }

    public Map<String, String> getUserInfoByToken(String token) {

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodeJWT = verifier.verify(token);

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userId", decodeJWT.getSubject());
        userInfo.put("role", decodeJWT.getClaim("role").asString());

        return userInfo;

    }
}
