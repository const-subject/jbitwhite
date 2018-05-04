package org.bitwhite.sdk.crypto;


import net.i2p.crypto.eddsa.EdDSAPublicKey;

import org.bitwhite.sdk.BTWConst;
import org.bitwhite.sdk.trs.Transaction;

import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.UUID;


public class Security {
    private static final MessageDigest sha256Digest;
    private static final RipeMD160 ripemd160Digest;

    static{
        MessageDigest messageDigest = null;
        RipeMD160 ripeMD160 = null;
        try {
             messageDigest = MessageDigest.getInstance("SHA-256");
             ripeMD160 = new RipeMD160();
        }
        catch (Exception ex) {}

        sha256Digest = messageDigest;
        ripemd160Digest = ripeMD160;
        
    }


    public KeyPair generateKeyPair(String secure) throws SecurityException {
        try {
            byte[] hash = sha256Hash(Encoding.getUTF8Bytes(secure));
            return Ed25519.generateKeyPairBySeed(hash);
        }
        catch (Exception ex){
            throw new SecurityException("generate key pair failed", ex);
        }
    }

    public String encodePublicKey(PublicKey publicKey)throws SecurityException {
        try {
            
            return Encoding.hex(((EdDSAPublicKey) publicKey).getAbyte());
        }
        catch (Exception ex){
            throw new SecurityException("encode public key failed", ex);
        }
    }

    public String getBase58Address(String publicKey) throws SecurityException{
        try{
            
            byte[] hash1 = sha256Hash(Decoding.hex(publicKey));
            byte[] hash2 = ripemd160Hash(hash1);
            byte[] checksum = sha256Hash(sha256Hash(hash2));

            byte[] buffer = new byte[hash2.length + 4];
            System.arraycopy(hash2, 0, buffer, 0, hash2.length);
            System.arraycopy(checksum, 0, buffer, hash2.length, 4);

            return Encoding.base58(buffer);
        }
        catch (Exception ex){
            throw new SecurityException("generate base58 checked address failed", ex);
        }
    }

    public String generateTransactionId(Transaction transaction)throws SecurityException {
        try {
            byte[] transactionBytes = transaction.getBytes(false, false);
            byte[] hash = sha256Hash(transactionBytes);
            
            return Encoding.hex(hash);
        } catch (Exception ex) {
            throw new SecurityException("generate transaction id failed", ex);
        }
    }

    public String generateSecret(){
        String uuid = UUID.randomUUID().toString().replace("-","");
        return Bip39.generateMnemonicCode(Decoding.unsafeDecodeHex(uuid));
    }

    public boolean isValidSecret(String secret) {
        return Bip39.isValidMnemonicCode(secret);
    }

    public String generateSignature(Transaction transaction, PrivateKey privateKey) throws SecurityException {
        try {

            byte[] transactionBytes = transaction.getBytes(true, true);
            byte[] hash = sha256Hash(transactionBytes);
            return Encoding.hex(Ed25519.signature(hash, privateKey));
        }
        catch (Exception ex){
            throw new SecurityException("setSignature transaction failed", ex);
        }
    }

    public String generateSignSignature(Transaction transaction, PrivateKey privateKey) throws SecurityException {
        try {
            byte[] transactionBytes = transaction.getBytes( false, true);
            byte[] hash = sha256Hash(transactionBytes);
            return Encoding.hex(Ed25519.signature(hash, privateKey));
        }
        catch (Exception ex){
            throw new SecurityException("setSignature transaction failed", ex);
        }
    }
   
    public Transaction prepareTransaction(Transaction transaction, PrivateKey privateKey, String secondSecret) throws SecurityException{
        transaction.setSignature(generateSignature(transaction, privateKey));

        if (null != secondSecret) {
            KeyPair secondKeyPair = generateKeyPair(secondSecret);
            transaction.setSignSignature(generateSignSignature(transaction, secondKeyPair.getPrivate()));
        }

        transaction.setId(generateTransactionId(transaction));
        return transaction;
    }
    
    public int getTransactionTimestamp() {
        return (int)((new Date().getTime() - BTWConst.BEGIN_EPOCH.getTime())/1000 - BTWConst.CLIENT_DRIFT_SECONDS);
    }

    private byte[] sha256Hash(byte[] message) {
    	
        sha256Digest.update(message);
        return sha256Digest.digest();
    }

    private byte[] ripemd160Hash(byte[] message){
        ripemd160Digest.update(message);
        return ripemd160Digest.digest();
    }

}