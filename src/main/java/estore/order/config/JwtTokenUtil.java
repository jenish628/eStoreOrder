package estore.order.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final long EXPIRATION = 24 * 60 * 60 * 1000; //1 DAY 24 HOUR
    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.issuer}")
    private String issuer;



    DecodedJWT validateToken(String token) {

        try {


            Algorithm algorithm = Algorithm.HMAC256(secret); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build(); //Reusable verifier instance
            return verifier.verify(token);

        } catch (JWTVerificationException ex) {
            LOGGER.error("invalid token", ex.getMessage());
            throw new JWTVerificationException(ex.getMessage());
        }
    }
}
