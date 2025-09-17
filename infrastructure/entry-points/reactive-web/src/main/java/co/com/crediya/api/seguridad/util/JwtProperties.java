package co.com.crediya.api.seguridad.util;

public record JwtProperties(
        String secret,
        Long expiration
) {
}
