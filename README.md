# FitFreak

FitFreak is an Android fitness app written in Kotlin. It provides features to track workouts, monitor progress, and manage fitness routines. This repository contains the Android app module and build configuration (Gradle/Kotlin DSL).

## Key points
- Language: Kotlin
- Build system: Gradle (Kotlin DSL)
- Project layout: root gradle files + `app/` Android application module
- Gradle wrapper included (`gradlew`, `gradlew.bat`)

## Features (example)
- Track workouts and session history
- Create and customize workout plans
- View performance stats and progress over time
- (Add features you plan to implement: e.g., heart-rate integration, sync, social sharing)

## Requirements
- Android Studio Arctic Fox or newer (recommended)
- JDK 11+ (or the JDK version set in `gradle.properties`)
- Android SDK matching project compileSdkVersion (open the project in Android Studio to install)
- A physical device or emulator running a compatible Android version

## Getting started (development)
1. Clone the repository:
   git clone https://github.com/rG33-dev/FitFreak.git

2. Open the project:
   - Open Android Studio → File → Open → select the cloned folder
   - Let Gradle sync and download required SDKs and dependencies

3. Build and run:
   - From Android Studio: select the `app` run configuration and click Run
   - Or from the command line:
     - ./gradlew assembleDebug
     - ./gradlew installDebug (requires a connected device/emulator)

## Project structure (overview)
- app/
  - src/...
  - build.gradle.kts — module build configuration
  - proguard-rules.pro — ProGuard/R8 rules
- build.gradle.kts — root build configuration
- settings.gradle.kts — module settings
- gradle/, gradlew, gradlew.bat — Gradle wrapper

## Configuration & build notes
- The repo uses Kotlin and Gradle Kotlin DSL (KTS). If you need to change SDK or Kotlin versions, update `app/build.gradle.kts` and root `build.gradle.kts`.
- ProGuard/R8 rules are in `app/proguard-rules.pro` — adjust when enabling release minification/obfuscation.

## Testing
- Add unit tests under `app/src/test` and instrumented tests under `app/src/androidTest`.
- Run tests:
  - ./gradlew test
  - ./gradlew connectedAndroidTest

## Contributing
Contributions are welcome. Suggested workflow:
1. Fork the repository
2. Create a topic branch for your change
3. Open a pull request describing your changes

Please include:
- A short description of the change
- Any setup steps required to test your change
- Screenshots if the change affects UI

## Roadmap / Ideas
- Add persistent storage (Room) for workouts and statistics
- Implement sync to cloud or backup/export functionality
- Add charts and progress visualizations
- Integrate with wearable/health APIs

## License
Add your license here (e.g., MIT). If you want me to add a license file, tell me which license to use.

## Contact
Repository owner: rG33-dev

