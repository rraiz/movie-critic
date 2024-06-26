package com.rraiz.movie_critic.feature.security.util;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.stereotype.Component;

@Component
public class RSAKeyProperties {

    /**
     * Public Key: Used by JwtDecoder to verify the signature of incoming JWT
     * tokens, ensuring they are valid and have not been tampered with.
     */
    private RSAPublicKey publicKey;

    /**
     * Private Key: Used by JwtEncoder to sign outgoing JWT tokens, ensuring they
     * are authentic and have not been tampered with.
     */
    private RSAPrivateKey privateKey;

    /**
     * Default constructor to generate a new RSA key pair.
     */
    public RSAKeyProperties() {
        KeyPair pair = KeyGeneratorUtility.generateKeyPair();
        this.publicKey = (RSAPublicKey) pair.getPublic();
        this.privateKey = (RSAPrivateKey) pair.getPrivate();
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }    
}
