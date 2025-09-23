package co.com.crediya.jwthelper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@ConfigurationProperties(prefix = "jwt")
@Profile("dev")
public record JwtProperties(
        String secret,
        Long expiration
) {
}
