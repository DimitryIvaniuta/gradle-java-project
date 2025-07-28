package com.gradleproject.config;

import lombok.Data;
import org.springframework.core.io.Resource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Binds the properties under security.jwt.* for your own token generation and validation.
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityJwtProperties {

    /**
     * JWT token configuration (issuer, audience, expirations, JWKS URI, etc.)
     */
    private JwtProperties jwt = new JwtProperties();

    /**
     * Keystore settings for RSA keypair storage.
     */
    private KeystoreProperties keystore = new KeystoreProperties();

    @Data
    public static class JwtProperties {
        /** The issuer (iss claim) for all JWTs. */
        private String issuer;
        /** The audience (aud claim) your tokens are intended for. */
        private String audience;
        /** Base‑64 encoded HMAC‑SHA256 secret (HS256) if you're using symmetric signing. */
        private String secret;
        /** How long (seconds) until an access token expires. */
        private long expirationSeconds;
        /** How long (seconds) until a refresh token expires. */
        private long refreshExpirationSeconds;
        /** Allowed clock skew (seconds) when validating incoming JWTs. */
        private long clockSkewSeconds;
        /** How often (ms) to rotate keys or refresh JWKS cache. */
        private long rotationPeriodMs;
        /** URI where the public key JWKS is exposed. */
        private String jwksUri;
    }

    @Data
    public static class KeystoreProperties {
        /** Path to the keystore; supports classpath: or file:. */
        private Resource path;
        /** Password for the keystore. */
        private String storePassword;
        /** Alias of the key within the keystore. */
        private String keyAlias;
        /** Password for the key (if different from storePassword). */
        private String keyPassword;
    }
}
