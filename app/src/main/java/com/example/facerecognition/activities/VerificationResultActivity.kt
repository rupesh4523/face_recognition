package com.example.facerecognition.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.facerecognition.R
import com.example.facerecognition.database.CandidateRepository
import java.io.File
import com.example.facerecognition.database.VerificationLogRepository
import com.example.facerecognition.models.VerificationLog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class VerificationResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_result)

        val candidateId =
            intent.getIntExtra(
                "CANDIDATE_ID",
                -1
            )

        val repository =
            CandidateRepository(this)

        val candidate =
            repository.getCandidateById(candidateId)

        val imgCandidate =
            findViewById<ImageView>(R.id.imgCandidate)

        val txtCandidateName =
            findViewById<TextView>(R.id.txtCandidateName)

        val txtApplicationNo =
            findViewById<TextView>(R.id.txtApplicationNo)

        val txtDepartment =
            findViewById<TextView>(R.id.txtDepartment)

        val txtStatus =
            findViewById<TextView>(R.id.txtStatus)
        val txtConfidence =
            findViewById<TextView>(
                R.id.txtConfidence
            )

        val btnBackDashboard =
            findViewById<Button>(R.id.btnBackDashboard)

        if (candidate != null) {

            val matchResult =
                intent.getBooleanExtra(
                    "MATCH_RESULT",
                    false
                )
            val confidence =
                intent.getFloatExtra(
                    "CONFIDENCE",
                    0f
                )
            val currentDateTime =
                SimpleDateFormat(
                    "dd-MM-yyyy hh:mm a",
                    Locale.getDefault()
                ).format(Date())

            val resultText =
                if (matchResult)
                    "MATCHED"
                else
                    "NOT MATCHED"

            val logRepository =
                VerificationLogRepository(this)

            val verificationLog =
                VerificationLog(

                    verificationId = 0,

                    candidateName =
                        candidate.name,

                    applicationNo =
                        candidate.applicationNo,

                    dateTime =
                        currentDateTime,

                    result =
                        resultText,

                    confidence =
                        confidence
                )

            logRepository.insertLog(
                verificationLog
            )

            txtCandidateName.text =
                "Name : ${candidate.name}"

            txtApplicationNo.text =
                "Application No : ${candidate.applicationNo}"

            txtDepartment.text =
                "Department : ${candidate.department}"

            txtStatus.text =
                if (matchResult)
                    "FACE MATCHED ✓"
                else
                    "FACE NOT MATCHED ✗"
            txtConfidence.text =
                "Confidence : %.2f%%"
                    .format(confidence)

            if (candidate.imagePath.isNotEmpty()) {

                imgCandidate.setImageURI(
                    Uri.fromFile(
                        File(candidate.imagePath)
                    )
                )
            }
        }

        btnBackDashboard.setOnClickListener {

            val intent =
                Intent(
                    this,
                    CandidateVerificationActivity::class.java
                )

            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP

            startActivity(intent)

            finish()
        }
    }
}