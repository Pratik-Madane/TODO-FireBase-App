TODO App with Firebase
A simple and efficient TODO application built with Android using Firebase as the backend. This app demonstrates CRUD (Create, Read, Update, Delete) operations with a clean and user-friendly interface.


Features
Create Tasks: Add new tasks to your TODO list.
Read Tasks: View all tasks in a structured and organized list.
Update Tasks: Edit task details such as name, description, or due date.
Delete Tasks: Remove tasks from the list once completed or no longer needed.
Real-time Database: Sync tasks across devices using Firebase.
User Authentication (Optional): Secure user-specific TODO lists using Firebase Authentication.



Technologies Used
Android Studio: Development environment.
Kotlin: Programming language.
Jetpack Compose: Modern UI toolkit for building native Android interfaces.
Firebase Realtime Database: Backend for storing and syncing tasks.
Firebase Authentication: For user login and secure task management (optional).


Prerequisites
Before you begin, ensure you have the following:



Android Studio installed on your machine.
A Firebase project created and configured.
Google Services JSON file (google-services.json) added to the app/ directory.
Internet connectivity for Firebase services.
Installation




Clone the repository:

bash
Copy code
git clone https://github.com/Pratik-Madane/TODO-FireBase-App.git
Open the project in Android Studio.

Sync Gradle files.

Ensure the google-services.json file is in the correct directory (app/).

Run the app on an emulator or a physical device.

Firebase Setup
Create a Firebase project at Firebase Console.
Add an Android app to your project and download the google-services.json file.
Enable Firebase Realtime Database:
Go to the Database section in the Firebase console.
Select "Realtime Database" and click "Create Database."
Set rules to allow read and write for testing:
json
Copy code
{
  "rules": {
    ".read": "true",
    ".write": "true"
  }
}
Note: Update rules for production to secure your database.
(Optional) Enable Firebase Authentication for user login.
How It Works
CRUD Operations
Create:

Add a new task by entering details and clicking "Add Task."
The task is stored in Firebase Realtime Database.
Read:

Fetch all tasks from Firebase and display them in a list.
Update:

Edit a task by selecting it from the list and modifying its details.
Delete:

Remove a task by selecting and confirming deletion.
Project Structure
css
Copy code
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/todo_app_firebase/
│   │   │   ├── MainActivity.kt
│   │   │   ├── TaskAdapter.kt
│   │   │   ├── FirebaseHelper.kt
│   │   │   ├── TaskModel.kt
│   │   │   └── CRUD Operations Implementation
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── drawable/
│   │   │   └── values/
├── build.gradle.kts
├── settings.gradle.kts
└── google-services.json
