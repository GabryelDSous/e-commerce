package dev.gabryel.ecommerce.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.gabryel.ecommerce.model.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.Optional;

@Configuration
public class TokenConfig {

    private final Algorithm algorithm;

    public TokenConfig(@Value("${jwt.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generateToken(UserModel userModel) {
        return JWT.create()
                .withClaim("user_id", userModel.getId().toString())
                .withSubject(userModel.getEmail())
                .withClaim("role", userModel.getRole().toString())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .sign(algorithm);
    }

    public Optional<JWTUserData> validationToken(String token) {
        try {
            DecodedJWT decode = JWT.require(algorithm).build().verify(token);
            return Optional.of(JWTUserData.builder()
                            .userId(decode.getClaim("user_id").asString())
                            .email(decode.getSubject())
                            .role(decode.getClaim("role").asString())
                            .build());
        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }
}
