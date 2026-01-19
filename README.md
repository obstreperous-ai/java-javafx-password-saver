# Password Saver

A secure desktop password manager application built with Java and JavaFX, providing encrypted storage for your sensitive credentials on macOS and Linux platforms.

## 🔐 Overview

Password Saver is a lightweight, standalone desktop application designed to help you securely store and manage your passwords. It uses industry-standard encryption algorithms to protect your data locally, ensuring your passwords never leave your machine. The application features a clean JavaFX interface and provides robust encryption using PBKDF2 key derivation and AES-256-GCM encryption.

## ✨ Key Features

- **Strong Encryption**: Uses PBKDF2 (100,000 iterations) for key derivation and AES-256-GCM for authenticated encryption
- **Local Storage**: All passwords are stored locally in encrypted format at `~/.passwordsaver/passwords.enc`
- **Cross-Platform**: Runs on macOS and Linux with Java 21
- **JavaFX UI**: Modern, intuitive graphical interface built with JavaFX
- **Secure by Design**: Built with security best practices including BouncyCastle cryptography library
- **No Cloud Dependencies**: Complete offline functionality - your data stays on your device
- **Open Source**: Licensed under Apache 2.0

## 📋 Prerequisites

- **Java**: Version 21 or higher (Temurin distribution recommended)
- **Gradle**: Included via Gradle wrapper (no separate installation needed)
- **Operating System**: macOS or Linux (tested on Ubuntu and macOS latest)

## 🚀 Getting Started

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/obstreperous-ai/java-javafx-password-saver.git
   cd java-javafx-password-saver
   ```

2. **Build the project**
   ```bash
   ./gradlew build --no-daemon
   ```

3. **Run the application**
   ```bash
   ./gradlew run
   ```

### Development Container Setup

This project includes a pre-configured development container for a consistent development environment.

1. **Prerequisites**
   - Docker Desktop
   - Visual Studio Code with Dev Containers extension

2. **Open in Dev Container**
   - Open the project in VS Code
   - When prompted, click "Reopen in Container"
   - Or use Command Palette: "Dev Containers: Reopen in Container"

3. **The container includes**
   - Ubuntu base image
   - Java 21 (Temurin)
   - Gradle
   - Required development tools (OpenSSL, GnuPG)
   - VS Code extensions for Java and JavaFX development

## 💻 Usage

### First Time Setup

1. Launch the application using `./gradlew run`
2. Create a master password when prompted (this encrypts all your stored passwords)
3. **Important**: Remember your master password - it cannot be recovered if lost

### Managing Passwords

- **Add Password**: Enter a name/label and password, then click "Save"
- **View Password**: Select an entry from the list and enter your master password
- **Update Password**: Save a new password with the same name to update
- **Delete Password**: Use the delete functionality in the UI

### Data Storage

- Encrypted password data is stored at: `~/.passwordsaver/passwords.enc`
- The file is only readable/writable by your user account
- All data is encrypted with your master password before being written to disk

## 🔒 Security

### Encryption Details

Password Saver implements a multi-layered security approach:

#### Key Derivation
- **Algorithm**: PBKDF2 (Password-Based Key Derivation Function 2)
- **Iterations**: 100,000 iterations
- **Key Size**: 256 bits
- **Salt**: 16-byte random salt generated per encryption operation
- **Implementation**: BouncyCastle `PKCS5S2ParametersGenerator`

#### Data Encryption
- **Algorithm**: AES-256-GCM (Galois/Counter Mode)
- **Key Size**: 256 bits (derived from master password)
- **IV Size**: 12 bytes (randomly generated per encryption)
- **Authentication Tag**: 128 bits (ensures data integrity)
- **No Padding**: GCM mode provides authenticated encryption without padding

#### Security Features
- Random salt generation using `SecureRandom`
- Unique IV (Initialization Vector) for each encryption operation
- Authenticated encryption prevents tampering
- Memory-safe string handling with UTF-8 encoding
- Base64 encoding for storage

### Security Best Practices

- **Master Password**: Choose a strong, unique master password (12+ characters, mixed case, numbers, symbols)
- **Backup**: Regularly backup `~/.passwordsaver/passwords.enc` to a secure location
- **Updates**: Keep the application updated for security patches
- **Environment**: Run only on trusted, malware-free systems

For more security details, see [docs/security.md](docs/security.md).

## 🏗️ Architecture

### Project Structure

```
java-javafx-password-saver/
├── src/
│   ├── main/
│   │   ├── java/com/example/passwordsaver/
│   │   │   ├── App.java                 # Main application entry point
│   │   │   ├── MainController.java      # JavaFX controller
│   │   │   ├── EncryptionService.java   # Encryption/decryption logic
│   │   │   ├── StorageService.java      # File I/O operations
│   │   │   └── PasswordEntry.java       # Password data model
│   │   └── resources/
│   │       ├── fxml/MainView.fxml       # UI layout
│   │       └── styles/app.css           # UI styling
│   └── test/
│       └── java/com/example/passwordsaver/
│           ├── EncryptionServiceTest.java
│           ├── StorageServiceTest.java
│           └── IntegrationTest.java
├── config/                               # Code quality configurations
│   ├── checkstyle/checkstyle.xml
│   └── pmd/pmd.xml
├── .devcontainer/                        # Dev container configuration
├── docs/                                 # Additional documentation
└── build.gradle                          # Build configuration
```

### Component Architecture

```
┌─────────────────────────────────────────┐
│           JavaFX UI Layer               │
│  (App.java, MainController.java, FXML) │
└───────────────┬─────────────────────────┘
                │
┌───────────────▼─────────────────────────┐
│         Application Logic Layer         │
│        (StorageService.java)            │
└───────────────┬─────────────────────────┘
                │
┌───────────────▼─────────────────────────┐
│        Encryption Layer                 │
│      (EncryptionService.java)           │
│   (PBKDF2 + AES-256-GCM via BC)        │
└───────────────┬─────────────────────────┘
                │
┌───────────────▼─────────────────────────┐
│         Data Storage Layer              │
│    (~/.passwordsaver/passwords.enc)     │
└─────────────────────────────────────────┘
```

### Key Components

- **EncryptionService**: Handles all cryptographic operations using BouncyCastle
- **StorageService**: Manages file I/O and data persistence
- **PasswordEntry**: Data model representing a stored credential
- **MainController**: UI logic and user interaction handling
- **App**: Application entry point and JavaFX initialization

## 🤖 Future Enhancements

### Planned AI/ML Integration Opportunities

This architecture is designed to accommodate future AI/ML capabilities:

#### 1. **Password Strength Analysis**
- Real-time password strength scoring using ML models
- Suggestions for improving weak passwords
- Detection of common password patterns

#### 2. **Breach Detection**
- Integration with Have I Been Pwned API
- Local ML model to detect compromised password patterns
- Automated breach notifications

#### 3. **Smart Password Generation**
- AI-powered password generation based on site requirements
- Learning from user preferences
- Context-aware suggestions

#### 4. **Anomaly Detection**
- ML-based detection of unusual access patterns
- Behavioral biometrics for additional security
- Alert system for suspicious activities

#### 5. **Natural Language Interface**
- Voice-controlled password retrieval
- Natural language queries for password search
- AI assistant for password management tasks

#### 6. **Intelligent Auto-fill**
- Browser integration with ML-based form detection
- Context-aware credential suggestions
- Privacy-preserving local inference

### Architecture Considerations for AI/ML

- **Modular Design**: New ML components can be added as separate services
- **Local-First AI**: Models can run locally to maintain privacy
- **Plugin Architecture**: Future support for extensible AI modules
- **Data Privacy**: ML models trained on anonymized, user-controlled data
- **Performance**: Async processing to keep UI responsive

## 🛠️ Development

### Building and Testing

```bash
# Build the project
./gradlew build --no-daemon

# Run tests
./gradlew test --no-daemon

# Run the application
./gradlew run

# Clean build
./gradlew clean build --no-daemon
```

### Code Quality Tools

This project enforces high code quality standards:

```bash
# Run all quality checks
./gradlew checkstyleMain pmdMain spotbugsMain --no-daemon

# Individual checks
./gradlew checkstyleMain --no-daemon   # Code style validation
./gradlew pmdMain --no-daemon          # Static analysis
./gradlew spotbugsMain --no-daemon     # Bug detection
```

**Quality Standards**:
- Maximum line length: 120 characters
- Spaces for indentation (4 spaces)
- Checkstyle, PMD, and SpotBugs compliance required
- All security vulnerabilities must be addressed

### Dependency Security Scanning

```bash
# Run OWASP dependency check
./gradlew dependencyCheckAnalyze --no-daemon
```

**Note**: Configure an NVD API key for faster dependency checks. See [docs/security.md](docs/security.md) for details.

### Technologies Used

- **Java**: 21 (Temurin distribution)
- **JavaFX**: 21.0.2 (controls, fxml, graphics modules)
- **BouncyCastle**: 1.78.1 (cryptography provider)
- **Jackson**: 2.17.2 (JSON serialization)
- **JUnit**: 5.10.2 (testing framework)
- **Build Tool**: Gradle 8.x with wrapper
- **Code Quality**: Checkstyle 10.17.0, PMD 7.4.0, SpotBugs 4.8.6

## 🤝 Contributing

We welcome contributions from the community! Here's how you can help:

### Getting Started

1. **Fork the repository** on GitHub
2. **Clone your fork** locally
3. **Create a feature branch**: `git checkout -b feature/your-feature-name`
4. **Make your changes** following the project conventions
5. **Test your changes**: Ensure all tests pass and quality checks succeed
6. **Commit your changes**: Use clear, descriptive commit messages
7. **Push to your fork**: `git push origin feature/your-feature-name`
8. **Open a Pull Request** with a detailed description

### Contribution Guidelines

#### Code Standards
- Follow existing code style and conventions
- Maintain line length under 120 characters
- Use meaningful variable and method names
- Add JavaDoc comments for public APIs
- Write unit tests for new functionality

#### Testing Requirements
- All new code must include unit tests
- Existing tests must pass: `./gradlew test`
- Integration tests should cover end-to-end workflows

#### Code Quality Checks
Before submitting a PR, ensure all quality checks pass:
```bash
./gradlew checkstyleMain pmdMain spotbugsMain test --no-daemon
```

#### Security Considerations
- Never commit secrets, API keys, or passwords
- Use the project's encryption services for sensitive data
- Follow secure coding practices
- Address any security warnings from SpotBugs or PMD

#### Pull Request Process
1. Update documentation for any changed functionality
2. Ensure CI/CD pipeline passes (GitHub Actions)
3. Request review from maintainers
4. Address review feedback promptly
5. Squash commits before merge (if requested)

### Types of Contributions

- **Bug Reports**: Open an issue with reproduction steps
- **Feature Requests**: Propose new features via issues
- **Documentation**: Improve README, JavaDocs, or guides
- **Code**: Fix bugs, implement features, refactor code
- **Testing**: Add test coverage, improve test quality
- **Security**: Report vulnerabilities privately

### Development Resources

- **Issue Tracker**: [GitHub Issues](https://github.com/obstreperous-ai/java-javafx-password-saver/issues)
- **Discussions**: [GitHub Discussions](https://github.com/obstreperous-ai/java-javafx-password-saver/discussions)
- **CI/CD**: Automated via GitHub Actions
- **Code Review**: All changes require review before merge

### Community Guidelines

- Be respectful and constructive
- Follow the project's code of conduct
- Help others in issues and discussions
- Give credit where credit is due

## 📄 License

This project is licensed under the **Apache License 2.0**. See the [LICENSE](LICENSE) file for details.

```
Copyright 2024 Password Saver Contributors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## 🙏 Acknowledgments

- **BouncyCastle**: Cryptography provider
- **OpenJFX**: JavaFX framework
- **Jackson**: JSON processing
- **OWASP**: Security tools and best practices

## 📞 Support

- **Issues**: Report bugs or request features on [GitHub Issues](https://github.com/obstreperous-ai/java-javafx-password-saver/issues)
- **Security**: Report security vulnerabilities privately to the maintainers
- **Documentation**: Additional docs in the `docs/` directory

## 🔗 Links

- **Repository**: [github.com/obstreperous-ai/java-javafx-password-saver](https://github.com/obstreperous-ai/java-javafx-password-saver)
- **Security Documentation**: [docs/security.md](docs/security.md)
- **License**: [Apache License 2.0](LICENSE)

---

**⚠️ Disclaimer**: This is a personal password manager designed for local use. While it implements strong encryption, users should evaluate their own security requirements and use at their own risk. Always maintain backups of your encrypted password file.
