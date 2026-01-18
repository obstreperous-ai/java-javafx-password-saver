package com.example.passwordsaver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageServiceTest {
    private StorageService storageService;
    private EncryptionService encryptionService;
    private static final String TEST_MASTER_PASSWORD = "testMasterPassword123";

    @BeforeEach
    void setUp() {
        encryptionService = new EncryptionService();
        storageService = new StorageService(encryptionService);
    }

    @AfterEach
    void tearDown() throws IOException {
        storageService.deleteDataFile();
    }

    @Test
    void testSaveAndRetrieveEntry() throws Exception {
        PasswordEntry entry = new PasswordEntry("Gmail", "myGmailPassword123");
        storageService.saveEntry(TEST_MASTER_PASSWORD, entry);

        PasswordEntry retrieved = storageService.retrieveEntry(TEST_MASTER_PASSWORD, "Gmail");
        assertNotNull(retrieved, "Retrieved entry should not be null");
        assertEquals("Gmail", retrieved.getName());
        assertEquals("myGmailPassword123", retrieved.getPassword());
    }

    @Test
    void testRetrieveNonExistentEntry() throws Exception {
        PasswordEntry retrieved = storageService.retrieveEntry(TEST_MASTER_PASSWORD, "NonExistent");
        assertNull(retrieved, "Non-existent entry should return null");
    }

    @Test
    void testSaveMultipleEntries() throws Exception {
        PasswordEntry entry1 = new PasswordEntry("Gmail", "password1");
        PasswordEntry entry2 = new PasswordEntry("GitHub", "password2");

        storageService.saveEntry(TEST_MASTER_PASSWORD, entry1);
        storageService.saveEntry(TEST_MASTER_PASSWORD, entry2);

        Map<String, PasswordEntry> allEntries = storageService.loadAllEntries(TEST_MASTER_PASSWORD);
        assertEquals(2, allEntries.size());
        assertEquals("password1", allEntries.get("Gmail").getPassword());
        assertEquals("password2", allEntries.get("GitHub").getPassword());
    }

    @Test
    void testUpdateExistingEntry() throws Exception {
        PasswordEntry entry1 = new PasswordEntry("Gmail", "oldPassword");
        storageService.saveEntry(TEST_MASTER_PASSWORD, entry1);

        PasswordEntry entry2 = new PasswordEntry("Gmail", "newPassword");
        storageService.saveEntry(TEST_MASTER_PASSWORD, entry2);

        PasswordEntry retrieved = storageService.retrieveEntry(TEST_MASTER_PASSWORD, "Gmail");
        assertEquals("newPassword", retrieved.getPassword());

        Map<String, PasswordEntry> allEntries = storageService.loadAllEntries(TEST_MASTER_PASSWORD);
        assertEquals(1, allEntries.size(), "Should only have one entry after update");
    }

    @Test
    void testDataFileExists() throws Exception {
        assertFalse(storageService.dataFileExists(), "Data file should not exist initially");

        PasswordEntry entry = new PasswordEntry("Test", "password");
        storageService.saveEntry(TEST_MASTER_PASSWORD, entry);

        assertTrue(storageService.dataFileExists(), "Data file should exist after saving");
    }

    @Test
    void testLoadAllEntriesEmptyFile() throws Exception {
        Map<String, PasswordEntry> entries = storageService.loadAllEntries(TEST_MASTER_PASSWORD);
        assertNotNull(entries, "Should return empty map, not null");
        assertTrue(entries.isEmpty(), "Should be empty when no data file exists");
    }
}
