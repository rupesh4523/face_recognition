package com.example.facerecognition.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.facerecognition.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.btnLogin)

        loginButton.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    CandidateVerificationActivity::class.java
                )
            )

        }
    }
}