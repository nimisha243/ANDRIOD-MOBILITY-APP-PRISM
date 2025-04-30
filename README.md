# Mobility App

A modern Android application for managing transportation and mobility services.

## Overview

The Mobility App is a comprehensive solution for managing transportation services, including ride scheduling, real-time tracking, and payment processing. Built with modern Android development practices and architecture patterns.

## Features

- 🚗 Ride Scheduling
- 📍 Real-time Location Tracking
- 💳 Secure Payment Processing
- 👤 User Profile Management
- 📊 Journey Analytics
- 🔔 Push Notifications

## Architecture

The app follows Clean Architecture principles with the following layers:

```
app/
├── data/           # Data layer
│   ├── local/      # Local database
│   ├── remote/     # API services
│   └── repository/ # Repository implementations
├── domain/         # Domain layer
│   ├── model/      # Domain models
│   ├── repository/ # Repository interfaces
│   └── usecase/    # Use cases
├── di/             # Dependency injection
├── ui/             # Presentation layer
│   ├── home/       # Home screen
│   ├── schedule/   # Schedule management
│   ├── profile/    # User profile
│   └── common/     # Shared UI components
└── utils/          # Utility classes
```

## Technology Stack

- **Language**: Kotlin
- **Architecture**: MVVM with Clean Architecture
- **Dependency Injection**: Hilt
- **Networking**: Retrofit + OkHttp
- **Database**: Room
- **Asynchronous**: Coroutines + Flow
- **UI Components**: Material Design
- **Navigation**: Navigation Component
- **Payment**: Stripe SDK
- **Location**: Google Play Services
- **Testing**: JUnit, Espresso

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or newer
- JDK 11 or newer
- Android SDK 31 or newer
- Google Play Services

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/mobility-app.git
```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Run the app on an emulator or physical device

## Documentation

Detailed documentation for each module can be found in the `docs/` directory:

- [Architecture Overview](docs/architecture.md)
- [API Documentation](docs/api.md)
- [Database Schema](docs/database.md)
- [UI Components](docs/ui-components.md)
- [Testing Guide](docs/testing.md)

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Material Design Components
- Google Maps Platform
- Stripe Payment Processing
- Android Jetpack Libraries 