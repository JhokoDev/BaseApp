# BaseApp

<div align="center">
  <img src="app/src/main/res/drawable/baseapp_logo.png" alt="BaseApp Logo" width="700" />
  <br>
  <a href="https://example.com">www.example.com</a><br>
  <em>Building Modern and Scalable Android Applications</em>
</div>

<p align="center">
  <a href="https://developer.android.com"><img alt="Android SDK 34" src="https://img.shields.io/badge/Android%20SDK-34-3DDC84?style=for-the-badge&logo=android"></a>
  <a href="https://www.java.com"><img alt="Java 17" src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=java"></a>
  <a href="https://kotlinlang.org"><img alt="Kotlin 1.9" src="https://img.shields.io/badge/Kotlin-1.9-0095D5?style=for-the-badge&logo=kotlin"></a>
  <a href="https://github.com/bumptech/glide"><img alt="Glide 4.16" src="https://img.shields.io/badge/Glide-4.16-40C4FF?style=for-the-badge"></a>
  <a href="https://material.io"><img alt="Material Design 1.12" src="https://img.shields.io/badge/Material%20Design-1.12-757575?style=for-the-badge&logo=material-design"></a>
</p>

## About BaseApp

**BaseApp** is a professional-grade Android starter kit designed to provide a robust, modular, and scalable foundation for building modern Android applications. Built with **Java** and fully compatible with **Kotlin**, BaseApp incorporates essential features such as hamburger menu navigation, edge-to-edge transparent bars, circular profile image uploads, notification management, and dynamic theme switching (light/dark modes). It adheres to Android best practices, leveraging **Material Design 3** components, dynamic permissions, and data persistence with `SharedPreferences`.

The goal of BaseApp is to accelerate Android app development by offering a customizable, production-ready structure that emphasizes usability, modern design, and compatibility with the latest Android APIs.

## Documentation

Comprehensive documentation for BaseApp’s features, patterns, and architecture is available in the [`/docs`](/docs) directory. We strongly recommend reviewing the following resources:

- [**Navigation Setup**](/docs/navigation.md): Configuring the hamburger menu and `BottomNavigationView`.
- [**Edge-to-Edge Bars**](/docs/edge-to-edge.md): Implementing transparent status and navigation bars.
- [**Profile Management**](/docs/profile-management.md): Managing profile editing and circular image uploads.
- [**Dynamic Permissions**](/docs/permissions.md): Handling permissions for notifications and storage.
- [**Notifications**](/docs/notifications.md): Setting up and triggering notifications with custom icons.
- [**Dynamic Theming**](/docs/theme-switching.md): Switching between light and dark themes.

## Technologies

BaseApp leverages a modern Android development stack to ensure performance, scalability, and maintainability:

- **Android SDK 34**: Provides access to the latest Android APIs for modern app development.
- **Java 17**: Core programming language for robust and reliable backend logic.
- **Kotlin 1.9**: Optional support for concise, safe, and interoperable code.
- **Glide 4.16**: Efficient image loading and circular cropping for profile images.
- **Material Design 3 (1.12)**: Modern UI components for a consistent and polished user experience.
- **Gradle 8.1**: Build automation with Kotlin DSL for streamlined dependency management.
- **RecyclerView 1.3.2**: Flexible and efficient list rendering.
- **SharedPreferences**: Lightweight data persistence for user settings and preferences.
- **AndroidX Libraries**: Core, AppCompat, and Lifecycle components for enhanced compatibility and functionality.

## Prerequisites

To get started, ensure the following tools are installed:

- **Android Studio**: Version 2023.1+ (Giraffe or later) - [Download](https://developer.android.com/studio)
- **JDK**: Version 17 or higher - [Download](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- **Git**: For version control - [Download](https://git-scm.com/)
- **Emulator or Android Device**: API 23+ (API 33+ recommended for modern permissions)

## Installation

Follow these steps to set up the development environment:

### 1. Clone the Repository

Clone the repository to your local machine:

```bash
git clone https://github.com/your_username/BaseApp.git
cd BaseApp
```

### 2. Open in Android Studio

- Launch Android Studio and open the cloned project.
- Sync Gradle: **File > Sync Project with Gradle Files**.

### 3. Configure Dependencies

Ensure the `app/build.gradle.kts` file includes the required dependencies:

```kotlin
dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
}
```

### 4. Build the Project

Clean and rebuild the project to resolve dependencies:

```bash
./gradlew clean
./gradlew build
```

If needed, add `android.suppressUnsupportedCompileSdk=34` to `gradle.properties` to suppress Android Gradle Plugin warnings.

### 5. Run the Application

- Select an emulator or device (API 23+).
- Click **Run > Run 'app'**.
- For testing modern permissions, use API 33+.

## Usage

### Home Screen (`HomeActivity`)

- Displays a welcome message.
- Features a Floating Action Button (FAB) with a customizable `Toast`.
- Includes a hamburger menu for navigating between screens.
- Showcases a `BottomNavigationView` with placeholder items ("Item 1" and "Item 2").

### Settings Screen (`SettingsActivity`)

- **RecyclerView** with toggle switches:
    - **Notifications**: Enable/disable notifications, request `POST_NOTIFICATIONS` permission, and trigger a test notification.
    - **Dark Mode**: Toggle between light and dark themes, with preferences saved in `SharedPreferences`.

### Profile Screen (`ProfileActivity`)

- **RecyclerView** displaying:
    - User name and email.
    - Circular profile image (using Glide with `CircleCrop`).
    - **Change Profile Picture**: Select an image from the gallery and save it to internal storage.
    - **Edit Profile**: Update name and email via a dialog.
    - **Logout**: Return to `HomeActivity`.

### Customization

- Add new items to the `RecyclerView` in `SettingsActivity` or `ProfileActivity`.
- Implement custom actions for `BottomNavigationView` items in `BaseUIActivity`.
- Extend FAB functionality in each activity as needed.

## Project Structure

```
BaseApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── com/example/baseapp/ui/
│   │   │   │   │   ├── BaseUIActivity.java
│   │   │   │   │   ├── HomeActivity.java
│   │   │   │   │   ├── SettingsActivity.java
│   │   │   │   │   ├── ProfileActivity.java
│   │   │   │   ├── com/example/baseapp/utils/
│   │   │   │   │   ├── NavigationUtils.java
│   │   │   │   │   ├── PermissionUtils.java
│   │   │   │   │   ├── NotificationUtils.java
│   │   │   │   ├── com/example/baseapp/adapter/
│   │   │   │   │   ├── SettingsAdapter.java
│   │   │   │   │   ├── ProfileAdapter.java
│   │   │   │   ├── com/example/baseapp/model/
│   │   │   │   │   ├── SettingItem.java
│   │   │   │   │   ├── ProfileItem.java
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_home.xml
│   │   │   │   │   ├── activity_settings.xml
│   │   │   │   │   ├── activity_profile.xml
│   │   │   │   │   ├── item_setting.xml
│   │   │   │   │   ├── item_profile.xml
│   │   │   │   │   ├── nav_header.xml
│   │   │   │   ├── drawable/
│   │   │   │   │   ├── ic_notification.xml
│   │   │   │   │   ├── ic_profile.xml
│   │   │   │   │   ├── circle_background.xml
│   │   │   │   │   ├── ic_home.xml
│   │   │   │   │   ├── ic_settings.xml
│   │   │   │   │   ├── ic_item1.xml
│   │   │   │   │   ├── ic_item2.xml
│   │   │   │   ├── menu/
│   │   │   │   │   ├── drawer_menu.xml
│   │   │   │   │   ├── navigation_menu.xml
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── themes.xml
│   │   │   │   │   ├── themes-night.xml
│   ├── build.gradle.kts
├── gradle/
├── gradle.properties
├── settings.gradle.kts
```

## Current Features

- **Intuitive Navigation**:
    - Hamburger menu for seamless transitions between `HomeActivity`, `SettingsActivity`, and `ProfileActivity`.
    - `BottomNavigationView` as a showcase, ready for expansion.
- **Profile Management**:
    - Displays name, email, and circular profile image with upload and persistence.
    - Edit profile data via dialog.
    - Logout functionality with navigation to the home screen.
- **Settings**:
    - Enable/disable notifications with dynamic permission handling.
    - Toggle between light and dark themes.
- **Dynamic Permissions**:
    - Supports `POST_NOTIFICATIONS` (API 33+).
    - Supports `READ_MEDIA_IMAGES` (API 33+) and `READ_EXTERNAL_STORAGE` (API < 33).
- **Notifications**:
    - Triggers test notifications with a custom icon.

## Planned Features

- **Media Management**:
    - CRUD operations for uploading and managing images, videos, and documents.
    - Media preview with support for various formats.
- **Advanced Authentication**:
    - Integration with social media login or two-factor authentication.
- **Custom Widgets**:
    - Display dynamic metrics and information on the home screen.

## Recommended Tools

- **Android Studio**: Primary IDE for development and debugging.
- **Gradle**: Build system with Kotlin DSL support (`build.gradle.kts`).
- **Glide**: Library for efficient image loading and circular cropping.
- **Material Components**: For modern, consistent UI components.
- **Lint**: Static code analysis integrated with Android Studio.
- **Git**: Version control for collaboration and change tracking.

## Troubleshooting

- **Build Errors**:
    - Run the following to generate a detailed log:
      ```bash
      ./gradlew build --stacktrace --debug > build_log.txt
      ```
    - Review `build_log.txt` for errors.

- **Non-Circular Profile Image**:
    - Verify Glide dependency in `app/build.gradle.kts`.
    - Ensure `CircleCrop` is correctly applied in `ProfileActivity.java`.

- **Permission Issues**:
    - Confirm `PermissionUtils.java` handles `READ_MEDIA_IMAGES` (API 33+) and `READ_EXTERNAL_STORAGE` (API < 33).
    - Test permission dialogs on API 33+ devices.

- **Missing Resources**:
    - Ensure icons (`ic_home`, `ic_settings`, `ic_item1`, `ic_item2`, `ic_profile`) are present in `app/src/main/res/drawable/`.

## Contributing

1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature/new-feature
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add new feature"
   ```
4. Push to the branch:
   ```bash
   git push origin feature/new-feature
   ```
5. Open a Pull Request on GitHub.

## License

This project is licensed under the [MIT License](LICENSE.md).

## Acknowledgments

We extend our gratitude to the following teams and communities:

- **Android Team**: For developing the Android SDK and robust tools.
- **Google Material Design**: For the Material Components library, ensuring a modern and consistent UI.
- **Glide Contributors**: For the Glide library, critical for circular image cropping.
- **Android Community**: For tutorials, forums, and resources that inspire development.

## Author

- **Your Name** - [@Jhokodev](https://github.com/JhokoDev)

---

<div align="center">
  <strong>BaseApp - Building Modern and Scalable Android Applications</strong>
</div>