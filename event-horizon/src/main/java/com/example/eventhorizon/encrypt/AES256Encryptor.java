package com.example.eventhorizon.encrypt;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AES256Encryptor {
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;
    @Value(value = "${ENCRYPTION.KEY}")
    private static String SECRET_KEY_ENV;
    @Value(value = "${ENCRYPTION.SALT}")
    private static String SALT_ENV;

    public static String encrypt(String plaintext) throws Exception {
        if (plaintext == null) return null;

        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        GCMParameterSpec ivSpec = new GCMParameterSpec(TAG_LENGTH, iv);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(), ivSpec);

        byte[] ciphertext = cipher.doFinal(plaintext.getBytes());

        byte[] encrypted = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, encrypted, 0, iv.length);
        System.arraycopy(ciphertext, 0, encrypted, iv.length, ciphertext.length);

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encryptedBase64) throws Exception {
        if (encryptedBase64 == null) return null;
        byte[] encrypted = Base64.getDecoder().decode(encryptedBase64);

        byte[] iv = new byte[IV_LENGTH];
        System.arraycopy(encrypted, 0, iv, 0, iv.length);
        GCMParameterSpec ivSpec = new GCMParameterSpec(TAG_LENGTH, iv);

        byte[] ciphertext = new byte[encrypted.length - IV_LENGTH];
        System.arraycopy(encrypted, IV_LENGTH, ciphertext, 0, ciphertext.length);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), ivSpec);

        byte[] plaintext = cipher.doFinal(ciphertext);
        return new String(plaintext);
    }

    private static SecretKey getSecretKey() throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(
                SECRET_KEY_ENV.toCharArray(),
                SALT_ENV.getBytes(),
                65536,
                256
        );
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }
}