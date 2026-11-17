# 🎥 ShadowCap

A stealth camera and audio recorder for Android with volume button shortcut. Uses Accessibility Service to start/stop recording secretly.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Features

- **Stealth Camera Recording** — Record video using front or back camera via Camera2 + MediaRecorder API
- **Audio Recording** — Capture microphone audio separately
- **Volume Button Shortcut** — Start/stop recording secretly using volume keys (Accessibility Service)
- **Hold or Toggle Mode** — Choose between hold-to-record or double-tap-toggle
- **Recording Management** — View, share, and delete recorded files
- **Dark Neon UI** — Beautiful Material Design interface
- **Foreground Service** — Recording continues in background with notification

## How It Works

1. Enable the Accessibility Service in Android Settings
2. Choose recording mode (Audio or Video) and camera (Front/Back)
3. Press Volume Up (video) or Volume Down (audio) to start recording secretly
4. Press the same volume button again to stop
5. Recorded files saved to device storage

## Requirements

- Android 6.0+ (API 26)
- Accessibility Service enabled
- Camera and Microphone permissions

## Development

Android project using Kotlin and Gradle.

### Requirements
- Android Studio or compatible IDE
- Android SDK 34
- Kotlin 1.9.22

## Releases

Latest APK: [v1.0.0](https://github.com/SABIR7718/ShadowCap/releases/tag/v1.0.0)