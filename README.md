# FitFreak  
Currently out of development cause I'M BORED.

FitFreak is an Android fitness app written in Kotlin.
It provides features to track workouts, monitor progress, and manage fitness routines.
This repository contains the Android app module and build configuration (Gradle/Kotlin DSL).

## Demo
https://github.com/user-attachments/assets/1e2100c6-7a09-4b6c-ba54-b0953a09b82e








## Key points
- Language: Kotlin
- Build system: Gradle (Kotlin DSL)
- Project layout: root gradle files + `app/` Android application module
- Gradle wrapper included (`gradlew`, `gradlew.bat`)

## Features (example)
- Track workouts PR's 
- Check your max lifts and overall performance
- View performance stats and progress over time
-  Future updates : (Add features you plan to implement: e.g., heart-rate integration, sync, social sharing)


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



## Contact
Repository owner: 
