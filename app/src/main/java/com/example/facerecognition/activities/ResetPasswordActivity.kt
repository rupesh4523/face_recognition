package com.example.facerecognition.activities

import android.app.KeyguardManager
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.facerecognition.R
import com.example.facerecognition.database.AdminRepository

class ResetPasswordActivity :
    AppCompatActivity() {

    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmPassword: EditText

    private val authenticationLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {

                resetPassword()

            } else {

                Toast.makeText(
                    this,
                    "Authentication Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_reset_password
        )

        etNewPassword =
            findViewById(
                R.id.etNewPassword
            )

        etConfirmPassword =
            findViewById(
                R.id.etConfirmPassword
            )

        val btnResetPassword =
            findViewById<Button>(
                R.id.btnResetPassword
            )

        btnResetPassword.setOnClickListener {

            authenticateUser()
        }
    }

    private fun authenticateUser() {

        val keyguardManager =
            getSystemService(
                Context.KEYGUARD_SERVICE
            ) as KeyguardManager

        val intent =
            keyguardManager
                .createConfirmDeviceCredentialIntent(
                    "Verify Identity",
                    "Authenticate to reset password"
                )

        if (intent != null) {

            authenticationLauncher.launch(
                intent
            )

        } else {

            Toast.makeText(
                this,
                "No device lock configured",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun resetPassword() {

        val newPassword =
            etNewPassword.text
                .toString()
                .trim()

        val confirmPassword =
            etConfirmPassword.text
                .toString()
                .trim()

        if (
            newPassword.isEmpty()
            || confirmPassword.isEmpty()
        ) {

            Toast.makeText(
                this,
                "Fill all fields",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (
            newPassword != confirmPassword
        ) {

            Toast.makeText(
                this,
                "Passwords do not match",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val repository =
            AdminRepository(this)

        val admin =
            repository.getAdmin()

        if (admin != null) {

            val success =
                repository.updateAdmin(
                    admin.first,
                    newPassword
                )

            if (success) {

                Toast.makeText(
                    this,
                    "Password Updated",
                    Toast.LENGTH_LONG
                ).show()

                finish()
            }
        }
    }
}