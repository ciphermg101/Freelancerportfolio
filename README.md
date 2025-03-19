
# Freelancer Portfolio Manager


1. Introduction
The Freelancer Portfolio Manager is an Android application developed using Jetpack Compose to help freelancers manage multiple professional profiles based on their various roles. The app allows users to create, update, and manage profile details, skills, certifications, social links, and more. It integrates with Room Database for persistent storage and follows Material Design 3 principles for an intuitive user experience.
2. Features & Functionalities
2.1 Home Screen
•	Displays a welcome message with an overview of saved freelancer profiles.
•	Floating Action Button (FAB) to add a new profile.
•	Bottom Navigation Bar to switch between different sections.

2.2 Profile Management
•	Create, Edit, and Delete freelancer profiles.
•	Store details including: 
o	Name, Role Title, Email, Phone, Location.
o	Summary, Skills, Certifications, Languages.
o	Social & Portfolio Links.
•	Implemented using Jetpack Compose Forms with: 
o	EditText Fields for text input.
o	Dropdown Menus for structured selections.
o	Multi-Select Chips for skills & certifications.
o	Image Picker for profile pictures.
2.3 Profile Display
•	Uses Cards UI to display freelancer profiles.
•	Circular profile picture with name & role title.
•	Expandable details section.
•	Icon Toggle Button to favorite profiles.
2.4 Edit & Delete Profiles
•	Clicking a profile card opens an Edit Dialog Box.
•	Includes pre-filled data for modification.
•	Deleting a profile triggers a Snackbar Confirmation Message.
2.5 Dark Mode Support
•	UserPreferences class manages dark mode settings.
•	Theme updates dynamically using Jetpack Compose Theme APIs.
3. Tech Stack & Dependencies
•	Programming Language: Kotlin
•	UI Framework: Jetpack Compose
•	State Management: ViewModel, LiveData
•	Database: Room Persistence Library
•	Dependency Injection: Dagger Hilt
•	Navigation: Jetpack Navigation Component
•	Material Design 3 for modern UI components
4. Architecture
The app follows the MVVM (Model-View-ViewModel) architecture:
•	Model Layer: Defines data entities (e.g., FreelancerProfile.kt, Certification.kt).
•	ViewModel Layer: Handles business logic and state management (FreelancerViewModel.kt).
•	View Layer: Implements UI components using Jetpack Compose (EditProfileScreen.kt, ProfileListScreen.kt).
5. User Experience Enhancements
•	Responsive UI with adaptive layout.
•	Scroll Support for long forms.
•	Dialogs for Better UX: 
o	Confirmation dialog before saving or deleting a profile.
o	AlertDialog for error handling.
•	Snackbar Messages for feedback.
6. Setup & Installation
6.1 Prerequisites
•	Android Studio Hedgehog or later
•	Kotlin 1.9+
•	Minimum SDK API Level 24 (Nougat)
6.2 Installation Steps
1.	Clone the repository: 
2.	git clone https://github.com/your-repo/freelancer-portfolio.git
3.	Open the project in Android Studio.
4.	Sync dependencies using Gradle Sync.
5.	Run the app on an emulator or physical device.
7. Future Enhancements
•	Integration with LinkedIn & Credly APIs for certifications import.
•	Export Profile as PDF feature.
•	Cloud Backup & Sync using Firebase.
•	AI-powered skill recommendations based on entered data.
8. Conclusion
The Freelancer Portfolio Manager provides a streamlined solution for freelancers to manage multiple career profiles efficiently. Built with Jetpack Compose, Room Database, and MVVM architecture, it ensures a responsive and scalable application with a polished user experience.
