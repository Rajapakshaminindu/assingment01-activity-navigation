<div align="center">

# 📱 Android Activity Navigation

**A modern Android application demonstrating multi-activity lifecycle management, two-way explicit Intent navigation with data transfer, Activity Result contracts, and tactile spring physics animations built with Jetpack Compose.**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Android Studio](https://img.shields.io/badge/Android%20Studio-Ladybug%20%7C%202024.2+-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white)](https://developer.android.com/studio)
[![Platform](https://img.shields.io/badge/Platform-Android%20(API%2024%2B)-34D399?style=for-the-badge&logo=android&logoColor=white)](https://www.android.com)

</div>

---

## 📖 Overview

This repository contains the completed project for the **Mobile Application Development** module assignment. The objective of this project is to explore and implement core Android fundamentals:

1. **Multi-Activity Lifecycle Management**: Defining, launching, and finishing independent activities.
2. **Explicit Intent Navigation**: Navigating between screens using explicit Android `Intent` declarations.
3. **Two-Way Data Transfer (Activity Result API)**: Passing request payloads and receiving structured response data back across activity boundaries.
4. **Activity Back Stack Operation**: Cleanly popping activities off the call stack using `finish()`.
5. **Modern Reactive UI**: Designing smooth, accessible interfaces with Jetpack Compose, message preset carousels, and spring physics animations.

---

## 🧭 Navigation Architecture

```mermaid
sequenceDiagram
    autonumber
    actor User
    participant MainActivity as 📱 MainActivity<br/>(Task Root)
    participant AndroidOS as ⚙️ Android OS<br/>(Activity Manager)
    participant SecondActivity as 📄 SecondActivity<br/>(Back Stack Top)

    User->>MainActivity: Selects preset / types message & taps "Launch Second Activity"
    MainActivity->>AndroidOS: launcher.launch(Intent with extras)
    AndroidOS->>SecondActivity: onCreate(savedInstanceState)
    SecondActivity->>SecondActivity: Extract EXTRA_MESSAGE & EXTRA_TIMESTAMP
    SecondActivity-->>User: Displays received payload & status options
    User->>SecondActivity: Selects status chip, types reply & taps "Send Reply & Return"
    SecondActivity->>AndroidOS: setResult(RESULT_OK, resultIntent) + finish()
    AndroidOS-->>MainActivity: onActivityResult callback triggered
    MainActivity-->>User: Displays "Response Received from SecondActivity" card
```

---

## ✨ Features

- 🎯 **Dual Activity Architecture**: Distinct `MainActivity` and `SecondActivity` registered securely in `AndroidManifest.xml`.
- ✉️ **Dynamic Request Transmission**: Custom messages typed or selected from preset chips in `MainActivity` are bundled into Intent extras and unpacked in `SecondActivity`.
- 🔄 **Two-Way Activity Result API**: `SecondActivity` returns response status chips and reply messages back to `MainActivity` via `rememberLauncherForActivityResult`.
- 🏷️ **Quick Message Presets & Controls**: Carousel of quick message templates, clear button, and character counter.
- 💫 **Tactile Spring Physics Animation**: Custom `AnimatedButton` component leveraging Compose's `animateFloatAsState`, `animateColorAsState`, and bouncy spring physics.
- 🎨 **Modern Visual Aesthetics**: Glassmorphic styling, curated gradients, responsive padding, edge-to-edge support, and Material 3 design tokens.
- 🔙 **Robust Stack Popping**: Full lifecycle handling returning seamlessly to the origin activity using `finish()`.
- 🧪 **Unit Test Suite**: Full test coverage verifying Intent extras, fallback defaults, and result contract serialization.

---

## 🧠 Key Technical Concepts

### 1. Explicit Intent with Extras & Activity Result API

```kotlin
// In MainActivity.kt - Launching for Result:
val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartActivityForResult()
) { result ->
    if (result.resultCode == Activity.RESULT_OK && result.data != null) {
        val reply = result.data?.getStringExtra(SecondActivity.EXTRA_REPLY_MESSAGE)
        val status = result.data?.getStringExtra(SecondActivity.EXTRA_REPLY_STATUS)
    }
}

val intent = SecondActivity.createIntent(context, customMessage)
launcher.launch(intent)
```

### 2. Extracting Payload & Sending Return Result

```kotlin
// In SecondActivity.kt - Receiving and Replying:
val receivedMessage = intent.getStringExtra(EXTRA_MESSAGE) ?: DEFAULT_MESSAGE

// Returning result to caller:
val resultIntent = createResultIntent(replyText, status)
setResult(Activity.RESULT_OK, resultIntent)
finish()
```

### 3. Bouncy Button Physics

```kotlin
val pressScale by animateFloatAsState(
    targetValue = if (isPressed) 0.92f else 1f,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    )
)
```

---

## 📂 Project Structure

```text
android-activity-navigation/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/activityassignment/
│   │   │   │   ├── MainActivity.kt          # Launcher activity & animated trigger
│   │   │   │   ├── SecondActivity.kt        # Secondary activity & result producer
│   │   │   │   └── ui/theme/
│   │   │   │       ├── Color.kt             # Brand and accent color definitions
│   │   │   │       ├── Theme.kt             # Material 3 dynamic theme setup
│   │   │   │       └── Type.kt              # Typography styles
│   │   │   ├── res/                         # Drawables, mipmaps, values
│   │   │   └── AndroidManifest.xml          # Application & Activity registration
│   │   └── test/
│   │       └── java/com/example/activityassignment/
│   │           ├── NavigationContractTest.kt # Intent constant and timestamp tests
│   │           └── ActivityResultContractTest.kt # Two-way result contract tests
│   └── build.gradle.kts                     # App module build configuration
├── gradle/
│   └── libs.versions.toml                   # Version catalog dependencies
├── settings.gradle.kts
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** (Ladybug 2024.2+ recommended)
- **JDK 17 or 21**
- **Android SDK** (Min API: 24, Target/Compile API: 35+)
- Android Emulator or physical device with USB Debugging enabled

### Installation & Run

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Rajapakshaminindu/assingment01-activity-navigation.git
   cd assingment01-activity-navigation
   ```

2. **Open in Android Studio:**
   - Launch Android Studio.
   - Select **Open** and choose the project directory.
   - Allow Gradle to sync dependencies.

3. **Run Unit Tests:**
   ```bash
   ./gradlew test
   ```

4. **Run the App:**
   - Select your target device or emulator from the device dropdown.
   - Click **Run ▶** (or press `Shift + F10`).

---

## 🎯 Learning Outcomes

- [x] Structured multi-activity architecture in Kotlin and Jetpack Compose.
- [x] Implemented explicit `Intent` triggers across activity components.
- [x] Passed and safely retrieved primitive and string data via Intent extras.
- [x] Implemented modern Android Activity Result contracts for two-way screen interaction.
- [x] Handled activity destruction and stack popping using `finish()`.
- [x] Engineered custom tactile micro-interactions with Compose animation APIs.

---

## 👤 Author

**Minindu Rajapaksha**
- GitHub: [@Rajapakshaminindu](https://github.com/Rajapakshaminindu)
- Repository: [assingment01-activity-navigation](https://github.com/Rajapakshaminindu/assingment01-activity-navigation)

---

<div align="center">

*Developed for the Mobile Application Development Module*

</div>
