package com.example.passwordsaver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EncryptionServiceTest {

    @Test
    void testDeriveKeyConsistency() {
        EncryptionService service = new EncryptionService();
        String password = "testPassword123";
        byte[] salt = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

        byte[] key1 = service.deriveKey(password, salt);
        byte[] key2 = service.deriveKey(password, salt);

        assertArrayEquals(key1, key2, "Same password and salt should produce same key");
    }

    @Test
    void testDeriveKeyDifferentPasswords() {
        EncryptionService service = new EncryptionService();
        byte[] salt = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

        byte[] key1 = service.deriveKey("password1", salt);
        byte[] key2 = service.deriveKey("password2", salt);

        assertNotEquals(
            java.util.Arrays.toString(key1),
            java.util.Arrays.toString(key2),
            "Different passwords should produce different keys"
        );
    }

    @Test
    void testEncryptDecrypt() throws Exception {
        EncryptionService service = new EncryptionService();
        String plaintext = "This is a secret password";
        String masterPassword = "myMasterPassword123";

        String encrypted = service.encrypt(plaintext, masterPassword);
        assertNotNull(encrypted, "Encrypted data should not be null");
        assertNotEquals(plaintext, encrypted, "Encrypted should be different from plaintext");

        String decrypted = service.decrypt(encrypted, masterPassword);
        assertEquals(plaintext, decrypted, "Decrypted should match original plaintext");
    }

    @Test
    void testEncryptionUniqueness() throws Exception {
        EncryptionService service = new EncryptionService();
        String plaintext = "test";
        String masterPassword = "password";

        String encrypted1 = service.encrypt(plaintext, masterPassword);
        String encrypted2 = service.encrypt(plaintext, masterPassword);

        assertNotEquals(
            encrypted1,
            encrypted2,
            "Same plaintext encrypted twice should produce different ciphertext (due to random IV)"
        );

        assertEquals(plaintext, service.decrypt(encrypted1, masterPassword));
        assertEquals(plaintext, service.decrypt(encrypted2, masterPassword));
    }
}
