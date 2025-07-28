package com.gradleproject.controller;

import com.gradleproject.config.SecurityJwtProperties;
import com.gradleproject.dto.AuthRequest;
import com.gradleproject.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final SecurityJwtProperties props;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        // Authenticate credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword())
        );

        // Collect authorities
        Set<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toUnmodifiableSet());
        String scope = String.join(" ", roles);

        // Build claims
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(props.getJwt().getIssuer())
                .subject(authentication.getName())
                .audience(List.of(props.getJwt().getAudience()))
                .issuedAt(now)
                .expiresAt(now.plusSeconds(props.getJwt().getExpirationSeconds()))
                .id(UUID.randomUUID().toString())             // jti
                .claim("scope", scope)                        // space-delimited (Spring friendly)
                .claim("roles", roles)                        // array form (useful for clients)
                .build();

        // Explicit RS256 header to match RSA keypair and decoder selector
        JwsHeader headers = JwsHeader.with(SignatureAlgorithm.RS256).build();

        // Encode & sign
        String token = jwtEncoder.encode(JwtEncoderParameters.from(headers, claims)).getTokenValue();

        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
    }
}
