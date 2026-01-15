# Security and Dependency Scanning

This project uses the OWASP Dependency-Check Gradle plugin. To avoid slow NVD
database updates and rate limits, configure an NVD API key.

## Local development

Create (or edit) `~/.gradle/gradle.properties` and add:

```
nvdApiKey=YOUR_NVD_API_KEY
```

Alternatively, set an environment variable:

```
export NVD_API_KEY=YOUR_NVD_API_KEY
```

## GitHub Actions

Add a repository secret named `NVD_API_KEY`. The CI workflow already forwards
this secret to the Dependency-Check task.
