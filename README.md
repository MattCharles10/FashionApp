👗 Fashion Market App
<p align="center"> <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin"> <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose"> <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android"> <img src="https://img.shields.io/badge/Material%20Design%203-757575?style=for-the-badge&logo=material-design&logoColor=white" alt="Material Design 3"> </p>
A modern e-commerce fashion marketplace built with Jetpack Compose for Android. This app provides a seamless shopping experience with a beautiful Material Design 3 interface.

📱 Screenshots

<img width="374" height="808" alt="Screenshot 2026-01-03 002223" src="https://github.com/user-attachments/assets/fb7d8a21-f83a-469a-84bd-a0da983b8634" />
<img width="368" height="793" alt="Screenshot 2026-01-03 001958" src="https://github.com/user-attachments/assets/ab79a8c5-f55c-41cf-acff-7c96ff7d759a" />
<img width="389" height="802" alt="Screenshot 2026-01-03 002244" src="https://github.com/user-attachments/assets/84a01ebe-eec7-4d46-9b5d-c026696a29ff" />
<img width="369" height="790" alt="Screenshot 2026-01-03 002348" src="https://github.com/user-attachments/assets/86d7b398-46d2-44b9-b400-565f37017bf4" />
<img width="372" height="775" alt="Screenshot 2026-01-03 002408" src="https://github.com/user-attachments/assets/d5e280be-0676-4ceb-bf6c-3aa8b30e4345" />
<img width="385" height="814" alt="Screenshot 2026-01-03 152341" src="https://github.com/user-attachments/assets/584b38e6-a82b-4f7f-9e9c-093e891f3fd4" />

✨ Features
🎨 Modern UI - Built with Material Design 3 and Jetpack Compose

🔐 User Authentication - Login and registration screens

🛍️ Product Browsing - Browse fashion items with categories

🛒 Shopping Cart - Add/remove items, calculate totals

👤 User Profile - Manage account details

🔄 Smooth Navigation - Jetpack Navigation Compose

🖼️ Image Loading - Coil for efficient image loading

📱 Responsive Design - Works on all screen sizes

🏗️ Architecture
text
com.example.fashionmarket/
├── MainActivity.kt
├── data/
│   ├── model/
│   │   ├── Product.kt
│   │   ├── User.kt
│   │   └── CartItem.kt
│   ├── repository/
│   └── database/
├── ui/
│   ├── screens/
│   │   ├── splash/
│   │   │   └── SplashScreen.kt
│   │   ├── auth/
│   │   │   ├── LoginScreen.kt
│   │   │   └── RegisterScreen.kt
│   │   ├── home/
│   │   │   ├── HomeScreen.kt
│   │   │   ├── components/
│   │   │   └── viewmodel/
│   │   ├── products/
│   │   ├── cart/
│   │   └── profile/
│   ├── components/
│   ├── navigation/
│   │   └── AppNavigation.kt
│   └── theme/
│       └── Theme.kt
├── viewmodel/
└── utils/


🛠️ Tech Stack
Kotlin - Primary programming language

Jetpack Compose - Modern declarative UI toolkit

Material Design 3 - Latest design system

Navigation Compose - For navigation between screens

Coil - For image loading

Clean Architecture - Separation of concerns

MVVM Pattern - Model-View-ViewModel architecture

📦 Dependencies
kotlin
dependencies {
    // Core
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")
    
    // Coil for images
    implementation("io.coil-kt:coil-compose:2.5.0")
    
    // Testing
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}


🚀 Getting Started
Prerequisites

Android Studio Flamingo or later
Android SDK 24+
Kotlin 1.9+

Installation
Clone the repository

bash
git clone https://github.com/yourusername/fashion-market-app.git
Open the project in Android Studio

Sync Gradle files

Build and run the app

📝 License
This project is licensed under the MIT License - see the LICENSE file for details.

🤝 Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

📧 Contact
Mathew Charles - matthewcharles.tech@gmail.com

Project Link: https://github.com/MattCharles10/FashionApp

🙏 Acknowledgments
Jetpack Compose
Material Design 3
Coil
Android Developers

<p align="center"> Made with ❤️ for fashion lovers </p>
