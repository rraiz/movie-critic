package com.rraiz.movie_critic.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.rraiz.movie_critic.feature.security.util.RSAKeyProperties;

@Configuration
public class JwtConfiguration {

    /**
     * RSA key properties for JWT encoding and decoding.
     * Contains the RSA public and private keys pair used for JWT token signing and
     * verification.
     */
    private final RSAKeyProperties keys;

    public JwtConfiguration(RSAKeyProperties keys) {
        this.keys = keys;
    }

    /**
     * Configures the JwtDecoder with the RSA public key.
     * This decoder is responsible for verifying JWT tokens using the provided RSA
     * public key.
     *
     * @return JwtDecoder configured JWT decoder
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        // Create a NimbusJwtDecoder with the RSA public key
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    /**
     * Configures the JwtEncoder with RSA key pair.
     * This encoder is responsible for signing JWT tokens using the provided RSA key
     * pair.
     *
     * @return JwtEncoder configured JWT encoder
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        // Create a JWK (JSON Web Key) representing the RSA key pair
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();

        // Create a JWKSource that provides the JWK set containing the RSA key pair
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

        return new NimbusJwtEncoder(jwks); // Return a NimbusJwtEncoder configured with the JWKSource
    }

}
