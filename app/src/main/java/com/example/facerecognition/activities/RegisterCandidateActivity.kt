package com.example.facerecognition.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.facerecognition.R
import com.example.facerecognition.database.CandidateRepository
import com.example.facerecognition.models.Candidate
import java.io.File
import android.graphics.BitmapFactory
import com.example.facerecognition.ml.FaceRecognitionHelper

class RegisterCandidateActivity : AppCompatActivity() {

    private lateinit var imgCandidateFace: ImageView

    private var capturedImagePath = ""

    private val faceCaptureLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {

                capturedImagePath =
                    result.data?.getStringExtra("IMAGE_PATH") ?: ""

                if (capturedImagePath.isNotEmpty()) {

                    imgCandidateFace.setImageURI(
                        Uri.fromFile(
                            File(capturedImagePath)
                        )
                    )
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_candidate)

        val etName =
            findViewById<EditText>(R.id.etName)

        val etApplicationNo =
            findViewById<EditText>(R.id.etApplicationNo)

        val etDepartment =
            findViewById<EditText>(R.id.etDepartment)

        val etExamName =
            findViewById<EditText>(R.id.etExamName)

        val captureButton =
            findViewById<Button>(R.id.btnCaptureFace)

        val saveButton =
            findViewById<Button>(R.id.btnSaveCandidate)

        imgCandidateFace =
            findViewById(R.id.imgCandidateFace)

        val candidateRepository =
            CandidateRepository(this)

        // Keyboard Navigation

        etName.setOnEditorActionListener { _, _, _ ->

            etApplicationNo.requestFocus()

            true
        }

        etApplicationNo.setOnEditorActionListener { _, _, _ ->

            etDepartment.requestFocus()

            true
        }

        etDepartment.setOnEditorActionListener { _, _, _ ->

            etExamName.requestFocus()

            true
        }

        etExamName.setOnEditorActionListener { _, _, _ ->

            saveButton.requestFocus()

            true
        }

        // Capture Face

        captureButton.setOnClickListener {

            val intent =
                Intent(
                    this,
                    FaceCaptureActivity::class.java
                )

            faceCaptureLauncher.launch(intent)
        }

        // Save Candidate

        saveButton.setOnClickListener {

            val name =
                etName.text.toString().trim()

            val applicationNo =
                etApplicationNo.text.toString().trim()

            val department =
                etDepartment.text.toString().trim()

            val examName =
                etExamName.text.toString().trim()

            if (
                name.isEmpty() ||
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
            if (capturedImagePath.isEmpty()) {

                Toast.makeText(
                    this,
                    "Capture Face First",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val bitmap =
                BitmapFactory.decodeFile(
                    capturedImagePath
                )

            val faceRecognitionHelper =
                FaceRecognitionHelper(this)

            val embedding =
                faceRecognitionHelper.getEmbedding(
                    bitmap
                )

            val embeddingString =
                embedding.joinToString(",")

            val candidate = Candidate(
                name = name,
                applicationNo = applicationNo,
                department = department,
                examName = examName,
                imagePath = capturedImagePath,
                faceEmbedding = embeddingString
            )

            val isInserted =
                candidateRepository.insertCandidate(
                    candidate
                )

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

                imgCandidateFace.setImageDrawable(null)

                capturedImagePath = ""

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