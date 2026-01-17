# Copilot Instructions for Password Saver

## Project Overview
This is a JavaFX-based password manager application built with Gradle. The application provides secure password storage with encryption using BouncyCastle.

## Tech Stack
- **Java**: Version 21 (Temurin distribution)
- **JavaFX**: Version 21.0.2 (modules: controls, fxml, graphics)
- **Build Tool**: Gradle with wrapper
- **Dependencies**:
  - Jackson (databind, datatype-jsr310) for JSON serialization
  - BouncyCastle (bcprov-jdk18on) for cryptography
  - JavaFX modules (controls, fxml, graphics)

## Build and Test Commands
- **Build the project**: `./gradlew build --no-daemon`
- **Run the application**: `./gradlew run`
- **Clean build**: `./gradlew clean build --no-daemon`

## Code Quality Tools
The project enforces code quality through multiple tools. Always run these before finalizing changes:

- **Checkstyle**: `./gradlew checkstyleMain --no-daemon`
- **PMD**: `./gradlew pmdMain --no-daemon`
- **SpotBugs**: `./gradlew spotbugsMain --no-daemon`
- **All quality checks**: `./gradlew checkstyleMain pmdMain spotbugsMain --no-daemon`
- **Dependency Check**: `./gradlew dependencyCheckAnalyze --no-daemon` (optional)

### Code Style Requirements (Checkstyle)
- Maximum line length: **120 characters**
- Use **spaces, not tabs** (4 spaces for indentation)
- No trailing whitespace
- Newline at end of each file
- Empty line separation between logical blocks
- Always use braces for control structures (if, for, while, etc.)
- Proper whitespace after keywords and around operators

### PMD Rules
The project uses PMD rulesets for:
- Best practices (Java best practices)
- Code style (consistent formatting and naming)
- Design patterns (good design principles)
- Error-prone patterns (common mistakes)
- Multithreading safety
- Security vulnerabilities

Excluded rules (intentionally relaxed):
- `UnusedPrivateMethod`
- `ShortClassName`
- `AtLeastOneConstructor`
- `MethodArgumentCouldBeFinal`
- `LocalVariableCouldBeFinal`
- `LawOfDemeter`

### SpotBugs
- Reports generated as HTML in `build/reports/spotbugs/`
- Fix all HIGH and MEDIUM priority bugs

## Code Conventions

### Package Structure
- Main application code: `com.example.passwordsaver`
- Main class: `com.example.passwordsaver.App`
- Controllers follow the pattern: `*Controller.java`

### JavaFX Conventions
- FXML files are located in `src/main/resources/fxml/`
- Stylesheets are located in `src/main/resources/styles/`
- Controllers use `@FXML` annotations for injected components
- Always use FXML for UI layout, not programmatic UI construction

### Security Practices
- **NEVER** hardcode passwords, API keys, or secrets in source code
- Use BouncyCastle for all cryptographic operations
- Follow secure coding practices as enforced by PMD security rules
- Validate all user input before processing
- Handle sensitive data appropriately (encrypt at rest, clear from memory)

## Directories and Files to NEVER Modify
- `.github/workflows/` - CI/CD pipeline configurations (unless explicitly asked)
- `config/checkstyle/checkstyle.xml` - Checkstyle rules (unless explicitly asked)
- `config/pmd/pmd.xml` - PMD rules (unless explicitly asked)
- `gradle/` - Gradle wrapper files
- `gradlew`, `gradlew.bat` - Gradle wrapper scripts
- `settings.gradle` - Gradle settings (unless explicitly asked)

## Directories to Exclude from Commits
- `build/` - Build outputs and reports (already in .gitignore)
- `.gradle/` - Gradle cache (already in .gitignore)
- `out/` - IDE output directories
- Any temporary or generated files

## Dependencies
When adding new dependencies:
1. Add to `build.gradle` in the `dependencies` block
2. Use specific version numbers, not version ranges
3. Prefer stable releases over snapshots
4. Check for known vulnerabilities before adding
5. Keep dependencies up to date but verify compatibility

## Testing
Currently, the project does not have a test suite configured. When adding tests:
- Use JUnit 5 (Jupiter) for unit tests
- Use TestFX for JavaFX UI tests
- Place test files in `src/test/java/` mirroring the main package structure
- Follow the naming convention: `*Test.java` for test classes
- Aim for meaningful test coverage of business logic and cryptographic operations

## Common Tasks

### Adding a New Feature
1. Understand existing code structure and conventions
2. Create necessary FXML files in `resources/fxml/` if UI changes are needed
3. Implement controller logic following existing patterns
4. Follow security best practices for any sensitive operations
5. Run all code quality checks before committing
6. Document any new dependencies or build requirements

### Fixing a Bug
1. Identify the root cause
2. Make minimal changes to fix the issue
3. Ensure the fix doesn't break existing functionality
4. Run code quality checks to ensure no new issues are introduced
5. Consider adding tests to prevent regression

### Refactoring
1. Maintain backward compatibility unless explicitly breaking changes are needed
2. Update related documentation
3. Run all quality checks before and after refactoring
4. Ensure the application still runs correctly with `./gradlew run`

## Example Code

### Controller Pattern
```java
package com.example.passwordsaver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ExampleController {
    @FXML
    private TextField inputField;
    
    @FXML
    private Button submitButton;
    
    @FXML
    private void initialize() {
        // Controller initialization logic
    }
    
    @FXML
    private void handleSubmit() {
        // Event handler logic
    }
}
```

### Resource Loading
```java
// Load FXML
FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/MainView.fxml"));

// Load CSS
scene.getStylesheets().add("/styles/app.css");
```

## Important Notes
- This is a security-sensitive application dealing with passwords
- Always prioritize security over convenience
- Review all cryptographic operations carefully
- Follow the principle of least privilege
- Validate and sanitize all inputs
- Use proper exception handling to avoid information leakage
