package com.example.facerecognition.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.facerecognition.R
import com.example.facerecognition.database.CandidateRepository
import com.example.facerecognition.models.Candidate
import android.content.Intent

class RegisterCandidateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_candidate)

        val etName = findViewById<EditText>(R.id.etName)
        val etApplicationNo = findViewById<EditText>(R.id.etApplicationNo)
        val etDepartment = findViewById<EditText>(R.id.etDepartment)
        val etExamName = findViewById<EditText>(R.id.etExamName)

        val captureButton = findViewById<Button>(R.id.btnCaptureFace)
        val saveButton = findViewById<Button>(R.id.btnSaveCandidate)

        val candidateRepository = CandidateRepository(this)

        captureButton.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    FaceCaptureActivity::class.java
                )
            )
        }

        saveButton.setOnClickListener {

            val name = etName.text.toString().trim()
            val applicationNo = etApplicationNo.text.toString().trim()
            val department = etDepartment.text.toString().trim()
            val examName = etExamName.text.toString().trim()

            if (name.isEmpty() ||
                applicationNo.isEmpty() ||
                department.isEmpty() ||
                examName.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val candidate = Candidate(
                name = name,
                applicationNo = applicationNo,
                department = department,
                examName = examName,
                imagePath = "",
                faceEmbedding = ""
            )

            val isInserted =
                candidateRepository.insertCandidate(candidate)

            if (isInserted) {

                Toast.makeText(
                    this,
                    "Candidate Saved Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                etName.text.clear()
                etApplicationNo.text.clear()
                etDepartment.text.clear()
                etExamName.text.clear()

            } else {

                Toast.makeText(
                    this,
                    "Failed To Save Candidate",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}