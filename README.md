# GraphQL Android Project

This project is an Android application built using Kotlin and Jetpack Compose. It leverages GraphQL for data fetching and Koin for dependency injection.

## Setup Instructions

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/graphqlandroid.git
    ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the project on an emulator or a physical device.

## Features Implemented

- Home Screen
- School List Screen
- Camp List Screen
- Create School Page
- Create Camp Page

## Architecture Overview

The project follows the MVVM (Model-View-ViewModel) architecture pattern. The main components are:

- **Model**: Represents the data and business logic.
- **View**: UI components built using Jetpack Compose.
- **ViewModel**: Manages UI-related data and handles business logic.

## Libraries or Tools Used

- **Kotlin**: Programming language
- **Jetpack Compose**: UI toolkit
- **GraphQL**: Data fetching
- **Koin**: Dependency injection
- **SQLDelight** : Local database

## Challenges Faced and How You Overcame Them

- **Navigation**: Implementing navigation in a Compose application required a different approach. I used `navController` to manage navigation between screens.
- **GraphQL Integration**: Integrating GraphQL required setting up the Apollo client and handling queries and mutations. We followed the Apollo documentation and examples to set it up correctly.

## Known Issues or Limitations

- **Error Handling**: Error handling is basic and needs improvement to provide better user feedback.
- **UI/UX**: Some UI components may not be fully optimized for different screen sizes and orientations.
