package com.example.facerecognition.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.facerecognition.R

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val editProfile = findViewById<Button>(R.id.btnEditProfile)
        val logout = findViewById<Button>(R.id.btnLogout)

        editProfile.setOnClickListener {
            Toast.makeText(
                this,
                "Profile Editing Coming Soon",
                Toast.LENGTH_SHORT
            ).show()
        }

        logout.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()
        }
    }
}