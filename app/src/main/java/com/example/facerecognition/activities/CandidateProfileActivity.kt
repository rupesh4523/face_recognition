package com.example.facerecognition.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.facerecognition.R
import com.example.facerecognition.database.CandidateRepository
import java.io.File
import androidx.activity.result.contract.ActivityResultContracts
import android.graphics.BitmapFactory
import com.example.facerecognition.ml.FaceRecognitionHelper

class CandidateProfileActivity : AppCompatActivity() {
    private var newImagePath = ""

    private val updateFaceLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {

                newImagePath =
                    result.data?.getStringExtra(
                        "IMAGE_PATH"
                    ) ?: ""
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_candidate_profile)

        val imgCandidate =
            findViewById<ImageView>(R.id.imgCandidate)

        val etName =
            findViewById<EditText>(R.id.etName)

        val etApplicationNo =
            findViewById<EditText>(R.id.etApplicationNo)

        val etDepartment =
            findViewById<EditText>(R.id.etDepartment)

        val etExamName =
            findViewById<EditText>(R.id.etExamName)

        val btnEdit =
            findViewById<Button>(R.id.btnEdit)

        val btnSaveChanges =
            findViewById<Button>(R.id.btnSaveChanges)

        val btnUpdateFace =
            findViewById<Button>(
                R.id.btnUpdateFace
            )

        val btnDelete =
            findViewById<Button>(R.id.btnDelete)

        val candidateId =
            intent.getIntExtra(
                "CANDIDATE_ID",
                -1
            )

        val repository =
            CandidateRepository(this)

        val candidate =
            repository.getCandidateById(candidateId)

        if (candidate != null) {

            etName.setText(candidate.name)

            etApplicationNo.setText(
                candidate.applicationNo
            )

            etDepartment.setText(
                candidate.department
            )

            etExamName.setText(
                candidate.examName
            )

            if (candidate.imagePath.isNotEmpty()) {

                imgCandidate.setImageURI(
                    Uri.fromFile(
                        File(candidate.imagePath)
                    )
                )
            }

            btnEdit.setOnClickListener {

                etName.isEnabled = true
                etApplicationNo.isEnabled = true
                etDepartment.isEnabled = true
                etExamName.isEnabled = true

                btnSaveChanges.visibility =
                    View.VISIBLE
            }

            btnSaveChanges.setOnClickListener {
                Toast.makeText(
                    this,
                    "New Path: $newImagePath",
                    Toast.LENGTH_LONG
                ).show()
                var updatedEmbedding =
                    candidate.faceEmbedding

                if (newImagePath.isNotEmpty()) {

                    val bitmap =
                        BitmapFactory.decodeFile(
                            newImagePath
                        )

                    val helper =
                        FaceRecognitionHelper(this)

                    val embedding =
                        helper.getEmbedding(bitmap)

                    updatedEmbedding =
                        embedding.joinToString(",")
                }

                val updatedCandidate =
                    candidate.copy(

                        imagePath =
                            if (newImagePath.isNotEmpty())
                                newImagePath
                            else
                                candidate.imagePath,

                        faceEmbedding =
                            updatedEmbedding,

                        name =
                            etName.text.toString(),

                        applicationNo =
                            etApplicationNo.text.toString(),

                        department =
                            etDepartment.text.toString(),

                        examName =
                            etExamName.text.toString()
                    )

                val success =
                    repository.updateCandidate(
                        updatedCandidate
                    )

                if (success) {

                    Toast.makeText(
                        this,
                        "Candidate Updated",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent =
                        Intent(
                            this,
                            CandidateVerificationActivity::class.java
                        )

                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_NEW_TASK

                    startActivity(intent)

                    finish()
                }
            }

            btnUpdateFace.setOnClickListener {

                val intent =
                    Intent(
                        this,
                        FaceCaptureActivity::class.java
                    )

                updateFaceLauncher.launch(
                    intent
                )
            }

            btnDelete.setOnClickListener {

                AlertDialog.Builder(this)
                    .setTitle("Delete Candidate")
                    .setMessage(
                        "Are you sure you want to delete this candidate?"
                    )
                    .setPositiveButton("Yes") { _, _ ->

                        val deleted =
                            repository.deleteCandidate(
                                candidate.candidateId
                            )

                        if (deleted) {

                            Toast.makeText(
                                this,
                                "Candidate Deleted",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent =
                                Intent(
                                    this,
                                    CandidateVerificationActivity::class.java
                                )

                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                        Intent.FLAG_ACTIVITY_NEW_TASK

                            startActivity(intent)

                            finish()
                        }
                    }
                    .setNegativeButton(
                        "No",
                        null
                    )
                    .show()
            }
        }
    }
}