package com.example.facerecognition.models

data class VerificationLog(

    val verificationId: Int = 0,

    val candidateName: String,

    val applicationNo: String,

    val dateTime: String,

    val result: String,

    val confidence: Float
)