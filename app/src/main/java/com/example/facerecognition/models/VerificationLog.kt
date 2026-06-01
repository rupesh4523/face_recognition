package com.example.facerecognition.models

data class VerificationLog(
    val verificationId: Int = 0,
    val candidateId: Int,
    val date: String,
    val time: String,
    val livenessStatus: String,
    val verificationStatus: String
)