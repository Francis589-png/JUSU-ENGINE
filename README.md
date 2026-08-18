# JUSU ENGINE

**Offline-first personal operating-system intelligence prototype.**

> Powered by JUSU tech team JTT

## Prototype goals

JUSU ENGINE is a private Android prototype exploring a local system-engineer concept:

- Diagnose and explain device problems
- System forensics
- Security risk scoring
- Sensitive-screen privacy protection architecture
- Personal gesture learning architecture
- Sandbox / simulate-before-execute
- Supported-operation undo and recovery architecture
- Audit trail
- Fail-safe execution model
- Strict offline operation

## Security boundary

This prototype deliberately does **not** request `INTERNET` permission and does not claim privileged/root access. Android restricts what a normal application can inspect or change. Features requiring privileged OS integration are represented as architecture/staging until they can be implemented through legitimate Android APIs or a system-level build.

Core safety model:

`Observe → Explain → Simulate → Confirm → Execute → Verify → Audit`

## Build

The GitHub Actions workflow provisions Gradle 9.5 directly and builds the debug APK. A Gradle wrapper is intentionally not committed yet; the CI workflow uses the official Gradle setup action to provision Gradle.

Locally, use Android Studio with JDK 17 and Android SDK 37, then run the project's Gradle tasks through the IDE or a matching Gradle installation.

## CI artifact

Every push to `main`, pull request, or manual workflow run executes unit tests and produces:

`app/build/outputs/apk/debug/app-debug.apk`

The APK is uploaded as the `jusu-engine-debug-apk` Actions artifact.

## Current status

Version `0.1.0` is the architecture/UI foundation. The next development stages can replace the staged modules with real Android API implementations without changing the central concept.
