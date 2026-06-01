package com.example.facerecognition.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.facerecognition.R

class VerificationResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_result)

        val backButton = findViewById<Button>(R.id.btnBackDashboard)

        backButton.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    CandidateVerificationActivity::class.java
                )
            )

            finish()
        }
    }
}