# AttendX - Examination Candidate Verification System

## Overview

AttendX is an Android-based offline candidate verification and attendance system developed using Kotlin, SQLite, CameraX, Google ML Kit, and Face Recognition techniques.

The application is designed for examination environments where candidates must be verified before entering examination halls. The system performs facial verification, liveness detection, candidate management, verification logging, and database management without requiring an internet connection.

---

## Current Project Status

### Completion Status

Approximately 95% Complete

### Current Build Status

* Application builds successfully
* Face Registration completed
* Face Verification completed
* Blink-based Liveness Detection completed
* Candidate Management completed
* Verification History completed
* CSV Export completed
* Database Backup & Restore completed
* Password Recovery completed
* Modern UI implementation completed

---

## Features Implemented

### Admin Authentication

* Admin Login
* Username & Password Validation
* Edit Profile
* Change Username & Password
* Password Recovery using Device Authentication

### Candidate Management

* Candidate Registration
* Candidate Profile Management
* Candidate Search
* Candidate List
* Edit Candidate Information
* Delete Candidate
* Face Update Support

### Face Verification System

* CameraX Integration
* Real-Time Face Detection
* Blink-Based Liveness Detection
* Face Embedding Comparison
* Candidate Verification
* Confidence Score Calculation
* Verification Result Display

### Verification History

* Verification Logs
* Date & Time Recording
* Match / No Match Status
* CSV Export
* Clear Verification History

### Database Management

* SQLite Offline Storage
* Database Backup
* Database Restore
* Candidate Repository
* Verification Repository
* Admin Repository

### User Interface

* Splash Screen
* Login Screen
* Dashboard
* Candidate Registration
* Candidate List
* Candidate Profile
* Verification Result
* Verification History
* Admin Profile
* Edit Profile
* Password Recovery

### Theme Support

* Light Mode
* Dark Mode
* Automatic Theme Switching

---

## Technology Stack

### Frontend

* Kotlin
* XML Layouts
* Material Design 3

### Backend

* SQLite

### Machine Learning

* Google ML Kit Face Detection
* Face Embedding Comparison

### Camera Framework

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
│   ├── CandidateVerificationActivity
│   ├── RegisterCandidateActivity
│   ├── CandidateListActivity
│   ├── CandidateProfileActivity
│   ├── FaceCaptureActivity
│   ├── LivenessCheckActivity
│   ├── VerificationResultActivity
│   ├── VerificationHistoryActivity
│   ├── ProfileActivity
│   ├── EditProfileActivity
│   └── ResetPasswordActivity
│
├── database
│   ├── DatabaseHelper.kt
│   ├── CandidateRepository.kt
│   ├── AdminRepository.kt
│   └── VerificationLogRepository.kt
│
├── models
│   ├── Candidate.kt
│   └── VerificationLog.kt
│
├── adapters
│   ├── CandidateAdapter.kt
│   └── VerificationLogAdapter.kt
│
└── utils
```

---

## Current Workflow

```text
Splash Screen
      ↓
Admin Login
      ↓
Verification Dashboard
      ↓
Candidate Registration
      ↓
Candidate Verification
      ↓
Face Detection
      ↓
Blink-Based Liveness Detection
      ↓
Face Matching
      ↓
Verification Result
```

---

## Liveness Detection Workflow

```text
Camera Preview
      ↓
Face Detection
      ↓
Blink To Verify
      ↓
Eyes Closed Detected
      ↓
Eyes Open Detected
      ↓
Liveness Passed
```

---

## Face Verification Workflow

```text
Registered Candidate
        ↓
Stored Face Embedding
        ↓
Capture Live Face
        ↓
Generate Embedding
        ↓
Compare Embeddings
        ↓
Calculate Confidence Score
        ↓
Match / No Match
```

---

## Verification History Workflow

```text
Verification Completed
         ↓
Save Log
         ↓
Store Result
         ↓
Store Confidence
         ↓
Store Timestamp
         ↓
Verification History
```

---

## Offline Architecture

The application is designed to work completely offline.

### Local Storage

* SQLite Database
* Candidate Information
* Face Embeddings
* Verification Logs
* Admin Credentials

### Offline Processing

* Face Detection
* Liveness Detection
* Face Matching
* Candidate Verification
* Database Management

No internet connection is required during operation.

---

## Additional Features

### CSV Export

Exports:

* Candidate Name
* Application Number
* Verification Result
* Confidence Score
* Date & Time

### Database Backup

* Backup SQLite Database
* Restore Database
* Offline Data Recovery

---

## Installation

1. Clone the repository

```bash
git clone <repository-url>
```

2. Open the project in Android Studio

3. Sync Gradle

4. Connect Android Device

5. Run Application

---

## Permissions

```xml
<uses-permission android:name="android.permission.CAMERA"/>
```

---

## Future Enhancements

* FaceNet Integration
* Advanced Anti-Spoofing
* Head Movement Detection
* Department Wise Analytics
* Attendance Reports
* PDF Report Generation
* Multi-Admin Support
* Cloud Synchronization

---

## Project Objective

To build a secure offline AI-powered candidate verification system that prevents impersonation during examinations using facial verification and liveness detection.

---

## Developed Using

* Android Studio
* Kotlin
* SQLite
* CameraX
* Google ML Kit
* Material Design 3
---

## Version

AttendX v1.0
