package bubbles.springapibackend.api.configuration.security;

import bubbles.springapibackend.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    private final String secret = "bubbles";

    public String generateToken(User userModel) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create().withIssuer("auth").withSubject(userModel.getEmail()).withExpiresAt(getExpirationDate()).sign(algorithm);
            return token;


        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token!", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm).withIssuer("auth").build().verify(token).getSubject();
        } catch (JWTVerificationException exception) {
            return "Falha na autenticação!";
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
