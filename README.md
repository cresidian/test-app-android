# VideoRecording App

Welcome to the VideoRecording app! This app allows users to record and playback short videos with a user-friendly interface. The app follows the MVVM design pattern and utilizes the Kotlin programming language along with several libraries for enhanced functionality. In this README, you'll find all the information you need to understand, build, and contribute to this app.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [App Features](#app-features)
- [Architecture](#architecture)
- [Used Libraries and Tools](#used-libraries-and-tools)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

### Prerequisites

Before you begin, make sure you have the following requirements:

- Android Studio (latest version)
- Android device or emulator running Android OS (API level 21+)

### Installation

1. Clone this repository using Git:
   ```bash
   git clone https://github.com/your-username/VideoRecordingApp.git
   ```

2. Open the project in Android Studio:
   - Launch Android Studio.
   - Click on "Open an Existing Project".
   - Navigate to the cloned repository folder and select it.
   - Android Studio will build the project and download the required dependencies.

## App Features

1. **Camera and Microphone Access**: On the first launch, the app will request access to the camera and microphone.

2. **Countdown Timer**: A 5-second countdown timer will be displayed on the camera frame before recording starts, as per the provided design.

3. **Video Recording**: After the countdown, users can record a 30-second video. The recording will automatically stop after 30 seconds. Users can also manually stop the recording using a designated button.

4. **Playback**: Users can play the recorded video using a separate button after recording has been stopped.

5. **Responsive Layout**: The app's layout is designed to be responsive and adapt to various Android screen sizes.

## Architecture

The app follows the MVVM (Model-View-ViewModel) design pattern, promoting a clear separation of concerns between UI, data, and logic. At least two components have been utilized in the codebase to ensure modularity and maintainability.

## Used Libraries and Tools

- **Coroutines**: Used for managing asynchronous operations and providing a clean and efficient concurrency model.

- **Hilt**: A dependency injection library that simplifies the process of injecting dependencies into Android components.

- **ViewBinding**: Replaces traditional findViewById with a type-safe way to access views, reducing the risk of runtime errors.

- **ExoPlayer**: A media playback library that provides a comprehensive and customizable interface for playing video content.

- **Coroutine Flows**: Used for communication between the business layer and presentation layer, providing a reactive and efficient way to handle data updates.

## Usage

1. Launch the app on your Android device or emulator.

2. Grant camera and microphone permissions when prompted.

3. The countdown timer will initiate as shown in the design.

4. After the countdown, the recording will begin automatically and stop after 30 seconds. Alternatively, you can manually stop the recording using the designated button.

5. To play the recorded video, use the playback button.

## Contributing

We welcome contributions to improve and enhance the app. To contribute, follow these steps:

1. Fork the repository.

2. Create a new branch for your feature/fix:
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. Make your changes and commit them:
   ```bash
   git commit -m "Add your commit message here"
   ```

4. Push your changes to your forked repository:
   ```bash
   git push origin feature/your-feature-name
   ```

5. Create a pull request from your branch to the main repository's `main` branch.

## License

This project is licensed under the [MIT License](LICENSE).

---

Feel free to explore, experiment, and enhance the app following the provided guidelines. If you have any questions or need assistance, please don't hesitate to contact us. Happy coding!
