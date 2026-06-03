package com.example.facerecognition.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.facerecognition.R
import android.widget.TextView
import com.example.facerecognition.database.CandidateRepository
import com.example.facerecognition.database.VerificationLogRepository
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import android.os.Environment

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val txtTotalCandidates =
            findViewById<TextView>(
                R.id.txtTotalCandidates
            )

        val txtTotalVerifications =
            findViewById<TextView>(
                R.id.txtTotalVerifications
            )

        val txtSuccessfulMatches =
            findViewById<TextView>(
                R.id.txtSuccessfulMatches
            )

        val txtFailedMatches =
            findViewById<TextView>(
                R.id.txtFailedMatches
            )

        val candidateRepository =
            CandidateRepository(this)

        val verificationRepository =
            VerificationLogRepository(this)

        val btnBackupDatabase =
            findViewById<Button>(
                R.id.btnBackupDatabase
            )

        val btnRestoreDatabase =
            findViewById<Button>(
                R.id.btnRestoreDatabase
            )

        txtTotalCandidates.text =
            "Candidates Registered : ${
                candidateRepository.getTotalCandidates()
            }"

        txtTotalVerifications.text =
            "Total Verifications : ${
                verificationRepository.getTotalVerifications()
            }"

        txtSuccessfulMatches.text =
            "Successful Matches : ${
                verificationRepository.getSuccessfulMatches()
            }"

        txtFailedMatches.text =
            "Failed Matches : ${
                verificationRepository.getFailedMatches()
            }"

        val editProfile =
            findViewById<Button>(
                R.id.btnEditProfile
            )

        val verificationHistory =
            findViewById<Button>(
                R.id.btnVerificationHistory
            )

        val logout =
            findViewById<Button>(
                R.id.btnLogout
            )

        editProfile.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    EditProfileActivity::class.java
                )
            )
        }

        verificationHistory.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    VerificationHistoryActivity::class.java
                )
            )
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
        btnBackupDatabase.setOnClickListener {

            try {

                val currentDB =
                    getDatabasePath(
                        "CandidateVerification.db"
                    )

                val backupFile =
                    File(
                        Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS
                        ),
                        "CandidateVerification_Backup.db"
                    )

                FileInputStream(currentDB).channel.use { src ->

                    FileOutputStream(backupFile).channel.use { dst ->

                        dst.transferFrom(
                            src,
                            0,
                            src.size()
                        )
                    }
                }

                Toast.makeText(
                    this,
                    "Backup Saved:\n${backupFile.absolutePath}",
                    Toast.LENGTH_LONG
                ).show()

            } catch (e: Exception) {

                Toast.makeText(
                    this,
                    "Backup Failed",
                    Toast.LENGTH_SHORT
                ).show()

                e.printStackTrace()
            }
        }
        btnRestoreDatabase.setOnClickListener {

            try {

                val backupFile =
                    File(
                        Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS
                        ),
                        "CandidateVerification_Backup.db"
                    )

                val currentDB =
                    getDatabasePath(
                        "CandidateVerification.db"
                    )

                FileInputStream(backupFile).channel.use { src ->

                    FileOutputStream(currentDB).channel.use { dst ->

                        dst.transferFrom(
                            src,
                            0,
                            src.size()
                        )
                    }
                }

                Toast.makeText(
                    this,
                    "Database Restored\nRestart App",
                    Toast.LENGTH_LONG
                ).show()

            } catch (e: Exception) {

                Toast.makeText(
                    this,
                    "Restore Failed",
                    Toast.LENGTH_SHORT
                ).show()

                e.printStackTrace()
            }
        }
    }

}