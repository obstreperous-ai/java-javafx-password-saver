package com.example.passwordsaver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class StorageService {
    private static final String DATA_DIR_NAME = ".passwordsaver";

    private static final String DATA_FILE_NAME = "passwords.enc";

    private final ObjectMapper objectMapper;

    private final EncryptionService encryptionService;

    private final Path dataFilePath;

    public StorageService(EncryptionService encryptionService) {
        this.objectMapper = new ObjectMapper();
        this.encryptionService = encryptionService;
        this.dataFilePath = getDataFilePath();
    }

    private Path getDataFilePath() {
        String userHome = System.getProperty("user.home");
        Path dataDir = Paths.get(userHome, DATA_DIR_NAME);
        return dataDir.resolve(DATA_FILE_NAME);
    }

    public void saveEntry(String masterPassword, PasswordEntry entry) throws Exception {
        Map<String, PasswordEntry> entries = loadAllEntries(masterPassword);
        entries.put(entry.getName(), entry);
        saveAllEntries(masterPassword, entries);
    }

    public PasswordEntry retrieveEntry(String masterPassword, String name) throws Exception {
        Map<String, PasswordEntry> entries = loadAllEntries(masterPassword);
        return entries.get(name);
    }

    public Map<String, PasswordEntry> loadAllEntries(String masterPassword) throws Exception {
        if (!Files.exists(dataFilePath)) {
            return new HashMap<>();
        }

        String encryptedContent = Files.readString(dataFilePath);
        if (encryptedContent.isEmpty()) {
            return new HashMap<>();
        }

        String decryptedJson = encryptionService.decrypt(encryptedContent, masterPassword);
        return objectMapper.readValue(decryptedJson, new TypeReference<>() { });
    }

    private void saveAllEntries(String masterPassword, Map<String, PasswordEntry> entries) throws Exception {
        Path parentDir = dataFilePath.getParent();
        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }

        String json = objectMapper.writeValueAsString(entries);
        String encrypted = encryptionService.encrypt(json, masterPassword);

        Files.writeString(dataFilePath, encrypted);
    }

    public boolean dataFileExists() {
        return Files.exists(dataFilePath);
    }

    public void deleteDataFile() throws IOException {
        if (Files.exists(dataFilePath)) {
            Files.delete(dataFilePath);
        }
    }
}
