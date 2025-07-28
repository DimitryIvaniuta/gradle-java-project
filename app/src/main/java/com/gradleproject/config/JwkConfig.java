package com.gradleproject.config;

import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@Configuration
//@EnableConfigurationProperties(SecurityJwtProperties.class)
@RequiredArgsConstructor
public class JwkConfig {

    private final SecurityJwtProperties securityProps;

    /**
     * Load your RSA keypair from the configured PKCS#12
     * and expose it as a Nimbus JWKSource for JwtEncoder.
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        try {
            // 1) Read keystore resource
            Resource ksResource = securityProps.getKeystore().getPath();
            char[] storePass = securityProps.getKeystore().getStorePassword().toCharArray();
            String alias = securityProps.getKeystore().getKeyAlias();
            char[] keyPass = securityProps.getKeystore().getKeyPassword().toCharArray();

            // 2) Load PKCS#12 keystore
            KeyStore ks = KeyStore.getInstance("PKCS12");
            try (InputStream is = ksResource.getInputStream()) {
                ks.load(is, storePass);
            }

            // 3) Extract key & certificate
            Key key = ks.getKey(alias, keyPass);
            Certificate cert = ks.getCertificate(alias);
            RSAPublicKey pub = (RSAPublicKey) cert.getPublicKey();
            RSAPrivateKey priv = (RSAPrivateKey) key;

            // 4) Build an RSAKey with private + public parts
            RSAKey rsaJwk = new RSAKey.Builder(pub)
                    .privateKey(priv)
                    .keyID(alias)
                    .build();

            // 5) Return as an immutable JWKSet
            return new ImmutableJWKSet<>(new JWKSet(rsaJwk));
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to load or parse the keystore for JWK generation", ex);
        }
    }

    /**
     * Expose the JWKSet (public only) so we can serve it via HTTP.
     */
    @Bean
    public JWKSet jwkSet(JWKSource<SecurityContext> jwkSource) {
        @SuppressWarnings("unchecked")
        ImmutableJWKSet<SecurityContext> imm = (ImmutableJWKSet<SecurityContext>) jwkSource;
        return imm.getJWKSet();
    }

    /**
     * REST controller that serves your JWKS at the configured URI,
     * e.g. GET http://localhost:8080/api/.well-known/jwks.json
     */
    @RestController
    @RequestMapping("${security.jwt.jwks-uri}")
    static class JwksController {
        private final JWKSet jwkSet;

        public JwksController(JWKSet jwkSet) {
            this.jwkSet = jwkSet;
        }

        @GetMapping
        public Map<String, Object> keys() {
            return jwkSet.toJSONObject();
        }
    }
}
