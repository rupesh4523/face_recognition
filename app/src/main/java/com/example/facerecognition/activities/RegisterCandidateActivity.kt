package com.example.facerecognition.activities

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.facerecognition.R

class RegisterCandidateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_candidate)

        val captureButton = findViewById<Button>(R.id.btnCaptureFace)
        val saveButton = findViewById<Button>(R.id.btnSaveCandidate)

        captureButton.setOnClickListener {
            Toast.makeText(
                this,
                "Camera will be connected later",
                Toast.LENGTH_SHORT
            ).show()
        }

        saveButton.setOnClickListener {
            Toast.makeText(
                this,
                "Candidate Saved",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

