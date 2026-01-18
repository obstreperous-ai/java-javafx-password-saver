package com.example.passwordsaver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MainController {
    private static final String ERROR_STYLE = "-fx-text-fill: red;";

    private static final String SUCCESS_STYLE = "-fx-text-fill: green;";

    private static final String WARNING_STYLE = "-fx-text-fill: orange;";

    @FXML
    private Label statusLabel;

    @FXML
    private PasswordField masterPasswordField;

    @FXML
    private TextField entryNameField;

    @FXML
    private PasswordField entryPasswordField;

    @FXML
    private Button saveButton;

    @FXML
    private Button retrieveButton;

    private StorageService storageService;

    @FXML
    private void initialize() {
        EncryptionService encryptionService = new EncryptionService();
        storageService = new StorageService(encryptionService);
        statusLabel.setText("Ready");
    }

    @FXML
    private void handleSave() {
        String masterPassword = masterPasswordField.getText();
        String entryName = entryNameField.getText();
        String entryPassword = entryPasswordField.getText();

        if (masterPassword.isEmpty() || entryName.isEmpty() || entryPassword.isEmpty()) {
            statusLabel.setText("Error: All fields are required");
            statusLabel.setStyle(ERROR_STYLE);
            return;
        }

        try {
            PasswordEntry entry = new PasswordEntry(entryName, entryPassword);
            storageService.saveEntry(masterPassword, entry);
            statusLabel.setText("Saved: " + entryName);
            statusLabel.setStyle(SUCCESS_STYLE);
            entryPasswordField.clear();
        } catch (Exception e) {
            statusLabel.setText("Error saving: " + e.getMessage());
            statusLabel.setStyle(ERROR_STYLE);
        }
    }

    @FXML
    private void handleRetrieve() {
        String masterPassword = masterPasswordField.getText();
        String entryName = entryNameField.getText();

        if (masterPassword.isEmpty() || entryName.isEmpty()) {
            statusLabel.setText("Error: Master password and entry name required");
            statusLabel.setStyle(ERROR_STYLE);
            return;
        }

        try {
            PasswordEntry entry = storageService.retrieveEntry(masterPassword, entryName);
            if (entry != null) {
                entryPasswordField.setText(entry.getPassword());
                statusLabel.setText("Retrieved: " + entryName);
                statusLabel.setStyle(SUCCESS_STYLE);
            } else {
                statusLabel.setText("Entry not found: " + entryName);
                statusLabel.setStyle(WARNING_STYLE);
            }
        } catch (Exception e) {
            statusLabel.setText("Error retrieving: " + e.getMessage());
            statusLabel.setStyle(ERROR_STYLE);
        }
    }

    @FXML
    private void handleClear() {
        entryNameField.clear();
        entryPasswordField.clear();
        statusLabel.setText("Cleared");
        statusLabel.setStyle("");
    }
}
