# Examination Candidate Verification System

## Project Overview

The Examination Candidate Verification System is an Android application developed to verify the identity of examination candidates using face detection and liveness detection.

The application works completely in offline mode using SQLite as the local database. It helps examination authorities prevent impersonation and unauthorized access during examinations.

---

## Features

### Admin Authentication

* Secure admin login
* Access restricted to authorized personnel

### Candidate Registration

* Register candidate details
* Store candidate information locally
* Capture candidate facial data

### Candidate Verification

* Face detection using ML Kit
* Camera integration using CameraX
* Real-time candidate verification

### Liveness Detection

* Blink-based liveness verification
* Prevents spoofing using static photographs
* Real-time eye tracking

### Offline Database

* SQLite database support
* No internet connection required
* Local storage of candidate information

### Verification Results

* Verification status display
* Candidate authentication results
* Examination eligibility confirmation

---

## Technologies Used

### Frontend

* Kotlin
* XML Layouts
* Android SDK

### Database

* SQLite

### Machine Learning

* Google ML Kit Face Detection

### Camera

* CameraX

### Development Environment

* Android Studio

---

## Project Structure

```text
com.example.facerecognition

├── activities
│   ├── SplashActivity
│   ├── LoginActivity
│   ├── RegisterCandidateActivity
│   ├── CandidateListActivity
│   ├── CandidateVerificationActivity
│   ├── LivenessCheckActivity
│   ├── VerificationResultActivity
│   └── ProfileActivity
│
├── database
│   ├── DatabaseHelper.kt
│   ├── CandidateRepository.kt
│   ├── AdminRepository.kt
│   └── VerificationRepository.kt
│
├── models
│   ├── Candidate.kt
│   ├── Admin.kt
│   └── VerificationLog.kt
│
├── ml
│   ├── CameraHelper.kt
│   └── FaceDetectorHelper.kt
│
├── adapters
└── utils
```

---

## Application Workflow

```text
Splash Screen
       ↓
Admin Login
       ↓
Dashboard
       ↓
Candidate Registration
       ↓
Store Candidate Data
       ↓
Candidate Verification
       ↓
Face Detection
       ↓
Liveness Detection
       ↓
Verification Result
```

---

## Liveness Detection Workflow

```text
Open Camera
      ↓
Detect Face
      ↓
Ask Candidate to Blink
      ↓
Eyes Closed Detected
      ↓
Eyes Open Detected
      ↓
Liveness Passed
```

---

## Future Enhancements

* Face Recognition Matching
* Face Embedding Storage
* Advanced Anti-Spoofing
* Candidate Attendance Tracking
* Examination Monitoring Dashboard
* Report Generation
* Export Verification Logs

---

## Installation

1. Clone the repository

```bash
git clone https://github.com/your-username/examination-candidate-verification.git
```

2. Open the project in Android Studio

3. Sync Gradle

4. Connect an Android device or emulator

5. Run the application

---

## Permissions Required

```xml
<uses-permission android:name="android.permission.CAMERA"/>
```

---

## Team Contributions

### Frontend Development

* User Interface Design
* Navigation Flow
* Activity Management

### Database Development

* SQLite Integration
* Candidate Data Storage
* Verification Logs

### Machine Learning

* Face Detection
* Liveness Detection
* Camera Integration

---

## Project Objective

The primary objective of this project is to provide a secure, offline, and efficient candidate verification mechanism for examination environments using facial verification and liveness detection techniques.

---

## Developed Using

* Android Studio
* Kotlin
* SQLite
* CameraX
* Google ML Kit Face Detection

---

**Academic Project – Examination Candidate Verification System**
**MIT Academy of Engineering, Pune**
