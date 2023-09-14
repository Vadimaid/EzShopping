package kg.ezshopping.ezshopping.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class JwtTokenHandler {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenHandler.class);

    @Value(value = "${jwt.token.secret}")
    private String secretKey;
    @Value(value = "${jwt.token.expired}")
    private Long expirationTime;

    public JwtTokenHandler() {
    }

    public String generateJwt(Authentication authentication) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + this.expirationTime);
        UserDetails authenticatedUser = (UserDetails) authentication.getPrincipal();

        return Jwts
                .builder()
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .setSubject(authenticatedUser.getUsername())
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .compact();
    }

    public String getLoginFromJwtClaims(String jwtValue) {
        return Jwts
                .parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(jwtValue)
                .getBody()
                .getSubject();
    }

    public boolean validateExistingToken(String jwtValue) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(this.secretKey)
                    .parse(jwtValue);
            return true;
        } catch (ExpiredJwtException | IllegalArgumentException | MalformedJwtException | SignatureException e) {
            logger.warn(e.getMessage());
        }
        return false;
    }
}
