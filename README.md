# Ashish Atte – Buildable Android Attendance App

This project is configured for Android Studio and GitHub Actions.

## Included fixes
- Firebase Email/Password sign-in screen.
- Admin UI is shown only when the Firebase Auth custom claim `admin=true` is present.
- Employee attendance remains available to signed-in employees.
- Location permission messaging no longer incorrectly asks for camera permission.
- Added Sign Out controls.
- Firestore rules keep admin operations protected by the `admin` custom claim.
- GitHub Actions now installs Gradle 8.10.2 directly, so a checked-in Gradle wrapper is not required.

## Firebase setup
1. Enable Email/Password Authentication.
2. Create Firestore.
3. Deploy `firestore.rules`.
4. Create `employees/{uid}` documents for employees. Example:
   - `uid`: Firebase Auth UID
   - `employeeCode`: employee code
   - `name`: employee name
   - `email`: employee email
   - `active`: true
5. Create `settings/office` with `latitude`, `longitude`, `radiusMeters`.
6. For admin users, set the Firebase Auth custom claim `admin=true` using a trusted backend/Admin SDK.

## Build locally
Open the `Ashish_Atte_Production` folder in Android Studio and run the `app` configuration, or use Gradle 8.10.2 with JDK 17:

`gradle assembleDebug`

APK output:
`app/build/outputs/apk/debug/app-debug.apk`

## Important
The included Firebase configuration belongs to the project represented by the supplied `google-services.json`. For production, enable Firebase App Check, review Firestore rules, configure a release signing key, and test location accuracy and offline behavior before deployment.
