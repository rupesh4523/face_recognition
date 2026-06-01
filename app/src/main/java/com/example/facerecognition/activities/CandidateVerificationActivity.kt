package com.example.facerecognition.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.facerecognition.R

class CandidateVerificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_verification)

        findViewById<Button>(R.id.btnRegister)
            .setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        RegisterCandidateActivity::class.java
                    )
                )
            }

        findViewById<Button>(R.id.btnCandidateList)
            .setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        CandidateListActivity::class.java
                    )
                )
            }

        findViewById<Button>(R.id.btnProfile)
            .setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        ProfileActivity::class.java
                    )
                )
            }

        findViewById<Button>(R.id.btnVerify)
            .setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        VerificationResultActivity::class.java
                    )
                )
            }
    }
}