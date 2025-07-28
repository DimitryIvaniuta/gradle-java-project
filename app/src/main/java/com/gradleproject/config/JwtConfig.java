package com.gradleproject.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;

import java.time.Duration;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final SecurityJwtProperties props;
    private final JWKSource<SecurityContext> jwkSource;

    /**
     * JWT encoder that will sign with your RSA private key.
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(jwkSource);
    }

    /**
     * JWT decoder that fetches public keys from your JWKS URI,
     * with issuer/audience/clock‑skew validation.
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withJwkSetUri(props.getJwt().getJwksUri())
                .build();

        // 1) validate timestamps with configured skew
        OAuth2TokenValidator<Jwt> timestampValidator =
                new JwtTimestampValidator(Duration.ofSeconds(props.getJwt().getClockSkewSeconds()));

        // 2) validate issuer
        OAuth2TokenValidator<Jwt> issuerValidator =
                new JwtIssuerValidator(props.getJwt().getIssuer());

        // 3) validate audience contains expected value
        OAuth2TokenValidator<Jwt> audienceValidator =
                new JwtClaimValidator<List<String>>(
                        "aud",
                        aud -> aud != null && aud.contains(props.getJwt().getAudience())
                );

        // combine them
        DelegatingOAuth2TokenValidator<Jwt> validator =
                new DelegatingOAuth2TokenValidator<>(
                        timestampValidator,
                        issuerValidator,
                        audienceValidator
                );

        decoder.setJwtValidator(validator);
        return decoder;
    }
}
