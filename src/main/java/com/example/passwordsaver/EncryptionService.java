package com.example.passwordsaver;

import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionService {
    private static final int SALT_LENGTH = 16;

    private static final int IV_LENGTH = 12;

    private static final int TAG_LENGTH = 128;

    private static final int KEY_LENGTH = 256;

    private static final int ITERATIONS = 100_000;

    private static final String ALGORITHM = "AES/GCM/NoPadding";

    private final SecureRandom secureRandom;

    public EncryptionService() {
        this.secureRandom = new SecureRandom();
    }

    public byte[] deriveKey(String masterPassword, byte[] salt) {
        PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator();
        generator.init(
            masterPassword.getBytes(StandardCharsets.UTF_8),
            salt,
            ITERATIONS
        );
        KeyParameter keyParam = (KeyParameter) generator.generateDerivedParameters(KEY_LENGTH);
        return keyParam.getKey();
    }

    public String encrypt(String plaintext, String masterPassword) throws Exception {
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);

        byte[] initVector = new byte[IV_LENGTH];
        secureRandom.nextBytes(initVector);

        byte[] key = deriveKey(masterPassword, salt);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_LENGTH, initVector);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec);

        byte[] ciphertext = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        ByteBuffer byteBuffer = ByteBuffer.allocate(SALT_LENGTH + IV_LENGTH + ciphertext.length);
        byteBuffer.put(salt);
        byteBuffer.put(initVector);
        byteBuffer.put(ciphertext);

        return Base64.getEncoder().encodeToString(byteBuffer.array());
    }

    public String decrypt(String encryptedData, String masterPassword) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        ByteBuffer byteBuffer = ByteBuffer.wrap(decoded);

        byte[] salt = new byte[SALT_LENGTH];
        byteBuffer.get(salt);

        byte[] initVector = new byte[IV_LENGTH];
        byteBuffer.get(initVector);

        byte[] ciphertext = new byte[byteBuffer.remaining()];
        byteBuffer.get(ciphertext);

        byte[] key = deriveKey(masterPassword, salt);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_LENGTH, initVector);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec);

        byte[] plaintext = cipher.doFinal(ciphertext);
        return new String(plaintext, StandardCharsets.UTF_8);
    }
}
