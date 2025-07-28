package com.gradleproject.config;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.*;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;

import java.time.Duration;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final SecurityJwtProperties props;
    private final JWKSource<SecurityContext> jwkSource;

    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // Build a Nimbus JWT processor that uses our JWKSource and RS256
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        JWSKeySelector<SecurityContext> keySelector =
                new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, jwkSource);
        jwtProcessor.setJWSKeySelector(keySelector);

        NimbusJwtDecoder decoder = new NimbusJwtDecoder(jwtProcessor);

        // Validators: exp/nbf with skew, issuer, audience
        OAuth2TokenValidator<Jwt> timestampValidator =
                new JwtTimestampValidator(Duration.ofSeconds(props.getJwt().getClockSkewSeconds()));
        OAuth2TokenValidator<Jwt> issuerValidator =
                new JwtIssuerValidator(props.getJwt().getIssuer());
        OAuth2TokenValidator<Jwt> audienceValidator =
                new JwtClaimValidator<List<String>>("aud",
                        aud -> aud != null && aud.contains(props.getJwt().getAudience()));

        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                timestampValidator, issuerValidator, audienceValidator));

        return decoder;
    }
}
