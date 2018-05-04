package org.bitwhite.sdk.crypto;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

public class Ed25519 {

    private static final String EDDSA_CURVE_TABLE = "Ed25519";
    private static final EdDSAParameterSpec EDDSA_PARAMETER_SPEC = EdDSANamedCurveTable.getByName(EDDSA_CURVE_TABLE);

    private static Signature signature ;
    static
    {
        try {
            signature = new EdDSAEngine(MessageDigest.getInstance(EDDSA_PARAMETER_SPEC.getHashAlgorithm()));
        }
        catch (Exception ex) {
            //
        }
    }

    public static KeyPair generateKeyPairBySeed(byte[] seed) {
        EdDSAPrivateKeySpec keySpec = new EdDSAPrivateKeySpec(seed, EDDSA_PARAMETER_SPEC);
        EdDSAPrivateKey privateKey = new EdDSAPrivateKey(keySpec);
        EdDSAPublicKey publicKey = new EdDSAPublicKey(new EdDSAPublicKeySpec(privateKey.getAbyte(), EDDSA_PARAMETER_SPEC));

        return new KeyPair(publicKey, privateKey);
    }

    public static byte[] signature(byte[] message, PrivateKey privateKey) throws InvalidKeyException, SignatureException {
        signature.initSign(privateKey);
        signature.update(message);
        return signature.sign();
    }

    public static boolean verify (byte[] message, byte[] sign, byte[] publicKey) throws InvalidKeyException,SignatureException {

        EdDSAPublicKeySpec spec = new EdDSAPublicKeySpec(publicKey, EdDSANamedCurveTable.getByName(EDDSA_CURVE_TABLE));
        EdDSAPublicKey pKey = new EdDSAPublicKey(spec);
        signature.initVerify(pKey);

        signature.update(message);
        return signature.verify(sign);
    }
}

