package com.example.facerecognition.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.facerecognition.R

class LivenessCheckActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liveness_check)

        val startButton =
            findViewById<Button>(R.id.btnStartLiveness)

        startButton.setOnClickListener {

            Toast.makeText(
                this,
                "Liveness Detection Successful",
                Toast.LENGTH_SHORT
            ).show()

            startActivity(
                Intent(
                    this,
                    VerificationResultActivity::class.java
                )
            )
        }
    }
}