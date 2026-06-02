# Examination Candidate Verification System

## Overview

The Examination Candidate Verification System is an Android-based offline identity verification solution designed for examination environments. The application uses facial verification, liveness detection, and local database storage to authenticate candidates before examinations.

The project is being developed as a hackathon solution and is designed to function completely offline without requiring internet connectivity.

---

## Current Project Status

### Completion Status

Approximately 70% Complete

### Current Build Status

* Application builds successfully
* SQLite integration completed
* Camera integration completed
* Face detection working
* Blink-based liveness detection working
* Light/Dark mode support implemented

---

## Features Implemented

### Admin Authentication

* Admin Login Screen
* Username and Password Validation
* SQLite-based Authentication

### Candidate Management

* Candidate Registration Screen
* Candidate List Screen
* Candidate Information Storage
* SQLite Database Integration

### Face Verification Components

* CameraX Integration
* Front Camera Preview
* Real-Time Face Detection using Google ML Kit
* Blink-Based Liveness Detection
* Candidate Verification Workflow

### User Interface

* Splash Screen
* Login Screen
* Candidate Verification Dashboard
* Register Candidate Screen
* Candidate List Screen
* Face Capture Screen
* Liveness Detection Screen
* Verification Result Screen

### Theme Support

* Light Mode
* Dark Mode
* Automatic Theme Switching Based on Device Settings

### Database

* SQLite Offline Storage
* Candidate Repository
* Admin Repository
* Verification Repository

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
│   ├── FaceCaptureActivity
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
│   └── FaceDetectorHelper.kt
│
├── adapters
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

## Features Under Development

### Face Registration

* Capture Candidate Face
* Save Face Image Locally
* Store Image Path in SQLite
* Face Preview in Registration Screen

### Face Recognition

* FaceNet Integration
* Face Embedding Generation
* Embedding Storage in SQLite
* Face Matching System
* Similarity Score Calculation

### Verification System

* Candidate Face Comparison
* Match / No Match Decision
* Confidence Score

### Verification Logs

* Verification History
* Timestamp Recording
* Candidate Activity Logs

---

## Offline Architecture

The application is designed to work completely offline.

### Local Storage

* SQLite Database
* Candidate Information
* Verification Logs
* Face Metadata

### Offline Processing

* Face Detection
* Liveness Detection
* Candidate Verification
* Data Management

No internet connection is required during operation.

---

## Future Enhancements

* Face Recognition using FaceNet
* Face Embedding Storage
* Head Turn Detection
* Advanced Anti-Spoofing
* Confidence Score Analysis
* Verification Reports
* Examination Attendance Tracking
* Analytics Dashboard

---

## Installation

1. Clone the repository

```bash
git clone <repository-url>
```

2. Open the project in Android Studio

3. Sync Gradle

4. Connect an Android device or emulator

5. Run the application

---

## Permissions

```xml
<uses-permission android:name="android.permission.CAMERA"/>
```

---

## Team Responsibilities

### Frontend Team

* UI Development
* Navigation
* User Experience

### Database Team

* SQLite Management
* Data Persistence
* Verification Logs

### AI Team

* Face Detection
* Liveness Detection
* Face Recognition
* Candidate Verification

---

## Project Objective

To build a secure, offline, AI-powered candidate verification system that prevents impersonation during examinations through facial verification and liveness detection.

---

## Developed Using

* Android Studio
* Kotlin
* SQLite
* CameraX
* Google ML Kit
* Material Design 3
