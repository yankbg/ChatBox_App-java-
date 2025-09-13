# ChatBox App (Java)

A real-time, one-on-one chat application built with Java and powered by Firebase. This Android app allows users to sign up, search for other users, and engage in private conversations.

## Features

- **User Authentication:** Secure phone number and OTP verification for user login.
- **Real-time Messaging:** Instantaneous message sending and receiving with Firebase Realtime Database.
- **User Search:** Easily find and start conversations with other registered users.
- **Modern Android UI:** A clean and intuitive user interface built with XML layouts.
- **Profile Management:** Basic user profile with profile picture support.
- **Organized Chat List:** Main screen displays a list of recent chats.

## 📸 App Screenshots

### 🔐 Login Screens

| Phone Number Input | OTP Verification | OTP Confirmation |
|--------------------|------------------|------------------|
| ![Login screen with phone number input field](https://github.com/user-attachments/assets/5c10c23c-0620-430e-be11-e3816fd74025) | ![OTP input screen with verification prompt](https://github.com/user-attachments/assets/39fce95a-0777-48c0-bb88-d6b841215cd5) | ![OTP confirmation screen showing success message](https://github.com/user-attachments/assets/4d80919a-5c26-4dff-9ab3-bd205dc0e889) |

### 💬 Chat & Search Activities

| Recent Chats | Search Screen | Chat Interface |
|--------------|---------------|----------------|
| ![Recent chat activity showing user and bot messages](https://github.com/user-attachments/assets/bc98ac98-d956-4991-b19a-d3ba8d335b88) | ![Search activity screen with input field and results](https://github.com/user-attachments/assets/ce381c48-3573-4eb0-b745-6709a45be9d4) | ![Chat interface with user input and bot response](https://github.com/user-attachments/assets/f9a92411-55ce-4b4e-ac92-cade81276005) |

## Getting Started

### Prerequisites

- Android Studio (latest version recommended)
- Android SDK (API level 21 or higher)
- Java JDK 8 or later
- A Firebase project with Firestore and Authentication enabled.

### Installation

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/yankbg/ChatBox_App-java-.git
    ```
2.  **Open the project in Android Studio.**
3.  **Set up Firebase:**
    *   Create a new project on the [Firebase Console](https://console.firebase.google.com/).
    *   Add an Android app to your Firebase project.
    *   Download the `google-services.json` file and place it in the `app/` directory.
    *   Enable **Authentication** (with the Phone Number sign-in method) and **Firestore**.
4.  **Build the project** to download dependencies and set up the environment.
5.  **Run the app** on an emulator or a physical Android device.

## Project Structure

```
ChatBox_App-java-/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── [Your Java source files]
│   │   │   └── res/
│   │   │       └── [Layout and resource files]
│   │   └── AndroidManifest.xml
├── build.gradle
└── README.md
```

## Technologies Used

- **Java**
- **Android SDK**
- **XML** (for UI layouts)
- **Firebase**
    - Firebase Authentication
    - Firebase Firestore
- **Material Components for Android**

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for improvements or bug fixes.

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

## Contact

For questions or feedback, please contact [yankbg](https://github.com/yankbg).
