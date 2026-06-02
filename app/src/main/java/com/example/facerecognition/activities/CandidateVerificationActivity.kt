package com.example.facerecognition.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.facerecognition.R
import com.example.facerecognition.database.CandidateRepository

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

                val editText = EditText(this)

                AlertDialog.Builder(this)
                    .setTitle("Verify Candidate")
                    .setMessage("Enter Application Number")
                    .setView(editText)

                    .setPositiveButton("Search") { _, _ ->

                        val applicationNo =
                            editText.text.toString().trim()

                        val repository =
                            CandidateRepository(this)

                        val candidate =
                            repository.getCandidateByApplicationNo(
                                applicationNo
                            )

                        if (candidate != null) {

                            Toast.makeText(
                                this,
                                "Candidate Found",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent =
                                Intent(
                                    this,
                                    LivenessCheckActivity::class.java
                                )

                            intent.putExtra(
                                "CANDIDATE_ID",
                                candidate.candidateId
                            )

                            intent.putExtra(
                                "CANDIDATE_NAME",
                                candidate.name
                            )

                            intent.putExtra(
                                "IMAGE_PATH",
                                candidate.imagePath
                            )

                            startActivity(intent)

                        } else {

                            AlertDialog.Builder(this)
                                .setTitle(
                                    "Candidate Not Found"
                                )
                                .setMessage(
                                    "Candidate is not registered. Register now?"
                                )

                                .setPositiveButton(
                                    "Register"
                                ) { _, _ ->

                                    startActivity(
                                        Intent(
                                            this,
                                            RegisterCandidateActivity::class.java
                                        )
                                    )
                                }

                                .setNegativeButton(
                                    "Cancel",
                                    null
                                )

                                .show()
                        }
                    }

                    .setNegativeButton(
                        "Cancel",
                        null
                    )

                    .show()
            }
    }
}