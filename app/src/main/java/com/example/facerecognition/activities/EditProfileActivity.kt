package com.example.facerecognition.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.facerecognition.R
import com.example.facerecognition.database.AdminRepository

class EditProfileActivity :
    AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_edit_profile
        )

        val etUsername =
            findViewById<EditText>(
                R.id.etUsername
            )

        val etPassword =
            findViewById<EditText>(
                R.id.etPassword
            )

        val btnSave =
            findViewById<Button>(
                R.id.btnSave
            )

        val repository =
            AdminRepository(this)

        val admin =
            repository.getAdmin()

        admin?.let {

            etUsername.setText(
                it.first
            )

            etPassword.setText(
                it.second
            )
        }

        btnSave.setOnClickListener {

            val username =
                etUsername.text.toString()

            val password =
                etPassword.text.toString()

            val success =
                repository.updateAdmin(
                    username,
                    password
                )

            if (success) {

                Toast.makeText(
                    this,
                    "Profile Updated",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        }
    }
}