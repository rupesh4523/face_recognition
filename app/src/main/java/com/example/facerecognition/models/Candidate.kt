package com.example.facerecognition.models

data class Candidate(
    val candidateId: Int = 0,
    val name: String,
    val applicationNo: String,
    val department: String,
    val examName: String,
    val imagePath: String,
    val faceEmbedding: String
)