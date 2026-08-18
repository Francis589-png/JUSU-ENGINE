# JUSU ENGINE

Offline-first Android prototype for a personal OS-engineer concept.

**Powered by JUSU tech team JTT**

## Scope

The prototype includes the architecture and UI for:
- local command interpretation
- system diagnostics snapshot
- forensics workflow
- security risk classification
- privacy guardian staging
- personal gesture learning staging
- sandbox / review-before-execute workflow
- recovery and undo architecture
- local audit trail
- fail-safe action boundaries

The prototype deliberately does **not** pretend a normal Android app has privileged OS/root access. Features requiring system-level privileges are represented by safe interfaces/staged modules until the appropriate Android APIs or system integration are available.

## Build

Open the project in Android Studio and sync Gradle. The project targets API 37, AGP 9.4.0, Gradle 9.6, Kotlin 2.4.10 and Compose BOM 2026.06.01.

## Offline requirement

No cloud AI, analytics SDK, remote API, or network service is required by the prototype. Network status is displayed only as a local device diagnostic.

## GitHub Actions

`.github/workflows/android.yml` builds debug and release APKs and uploads them as workflow artifacts.

## Security principles

`Observe -> Explain -> Simulate -> Confirm -> Execute -> Verify -> Audit`

Dangerous actions are not silently executed by the prototype.
# JUSU-ENGINE
