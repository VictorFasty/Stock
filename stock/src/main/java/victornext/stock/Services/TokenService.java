package victornext.stock.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import victornext.stock.Model.User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generatedToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("auth-api")
                .withSubject(user.getEmail())
                .withExpiresAt(genExpirationDate())
                .sign(algorithm);
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
