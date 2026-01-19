package com.example.passwordsaver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration test demonstrating the complete password save/retrieve flow.
 */
class IntegrationTest {
    private StorageService storageService;

    private static final String MASTER_PASSWORD = "MySecretMasterPassword123!";

    @BeforeEach
    void setUp() {
        EncryptionService encryptionService = new EncryptionService();
        storageService = new StorageService(encryptionService);
    }

    @AfterEach
    void tearDown() throws Exception {
        storageService.deleteDataFile();
    }

    @Test
    void testCompletePasswordFlow() throws Exception {
        // Save a password entry
        PasswordEntry gmailEntry = new PasswordEntry("Gmail", "myGmailPassword123");
        storageService.saveEntry(MASTER_PASSWORD, gmailEntry);

        // Save another password entry
        PasswordEntry githubEntry = new PasswordEntry("GitHub", "myGitHubToken456");
        storageService.saveEntry(MASTER_PASSWORD, githubEntry);

        // Retrieve the first entry
        PasswordEntry retrievedGmail = storageService.retrieveEntry(MASTER_PASSWORD, "Gmail");
        assertNotNull(retrievedGmail, "Gmail entry should be retrievable");
        assertEquals("Gmail", retrievedGmail.getName(), "Entry name should match");
        assertEquals("myGmailPassword123", retrievedGmail.getPassword(), "Password should match");

        // Retrieve the second entry
        PasswordEntry retrievedGitHub = storageService.retrieveEntry(MASTER_PASSWORD, "GitHub");
        assertNotNull(retrievedGitHub, "GitHub entry should be retrievable");
        assertEquals("GitHub", retrievedGitHub.getName(), "Entry name should match");
        assertEquals("myGitHubToken456", retrievedGitHub.getPassword(), "Password should match");

        // Update an existing entry
        PasswordEntry updatedGmail = new PasswordEntry("Gmail", "newGmailPassword789");
        storageService.saveEntry(MASTER_PASSWORD, updatedGmail);

        // Verify the update
        PasswordEntry retrievedUpdated = storageService.retrieveEntry(MASTER_PASSWORD, "Gmail");
        assertEquals("newGmailPassword789", retrievedUpdated.getPassword(), "Password should be updated");
    }
}
