# WhaCOOK

![whacook logo](src/whacook.png)

means What to Cook but → *Wha~~t to~~COOK*

## Table of contents

- [WhaCOOK](#whacook)
  - [Table of contents](#table-of-contents)
  - [Motivation and background](#motivation-and-background)
  - [Features](#features)
  - [Demo video](#demo-video)
  - [How to run](#how-to-run)
    - [Prerequisites](#prerequisites)
    - [Build and Run Android Application](#build-and-run-android-application)
    - [Build and Run Desktop (JVM) Application](#build-and-run-desktop-jvm-application)
    - [Build and Run Web Application](#build-and-run-web-application)
  - [Built using](#built-using)
  - [Licence](#licence)

---

## Motivation and background

We've all been there: staring at a fridge full of random ingredients, wondering what to make for dinner. **WhaCOOK** solves this problem by using the power of AI (Google Gemini) to suggest recipes based specifically on the ingredients you already have at home. No more food waste, and no more endless scrolling through recipe sites.

## Features

- **Ingredient Management**: Add and manage the ingredients you currently have in your kitchen.
- **AI Recipe Generation**: Generate creative and delicious recipes using Google Gemini AI, tailored to your available ingredients.
- **Recipe Details**: View detailed instructions, cooking time, and required ingredients.
- **Rating & Favourite Recipes**: You can rate recipes individually and mark them as favourite.
- **Sort and Filter Recipes**: You can sort the recipes by rating and filter them by favourite or rating.
- **Edit Recipes**: You can edit the generated recipes any time if you found a wrong recipe.
- **Multiplatform**: Seamlessly runs on Android, Desktop (JVM), and Web (Wasm) with a unified shared codebase.
- **Local Storage**: Saves your ingredients and preferences locally on your device.

## Demo video

*(Add link to demo video here when available)*

## How to run

### Prerequisites

To use the recipe generation feature, you will need a **Google Gemini API Key**. You can get one from [Google AI Studio](https://aistudio.google.com/app/apikey).

The app will ask for this key at the first time you open the application, and you can modify it any time under the Menu / API Key menu point.

### Build and Run Android Application

To run the application on an Android device or emulator:

```bash
./gradlew installDebug
```

Or run the `androidApp` configuration directly from Android Studio.

### Build and Run Desktop (JVM) Application

To run the application on your desktop:

```bash
./gradlew :composeApp:run
```

### Build and Run Web Application

To run the application in your browser (using WebAssembly):

```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

Then open the link shown in the terminal (usually `http://localhost:8080`).

## Built using

This project is a **Kotlin Multiplatform** application leveraging modern technologies:

- **[Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)**: For sharing logic across Android, Desktop, and Web.
- **[Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)**: For a unified UI framework.
- **[Voyager](https://voyager.adriel.cafe/)**: For multiplatform navigation.
- **[Koin](https://insert-koin.io/)**: For dependency injection.
- **[Ktor](https://ktor.io/)**: For networking and API calls.
- **[Room](https://developer.android.com/kotlin/multiplatform/room)** & **[SQLDelight](https://cashapp.github.io/sqldelight/)**: For local data persistence. Room is for Android and JVM targets and SQLDelight for web target as Room does not support web targets.
- **[Google Gemini API](https://ai.google.dev/)**: For AI-powered recipe generation.

## Licence

This project is licensed under the [MIT License](./LICENSE).
