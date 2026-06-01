package com.example.facerecognition.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.facerecognition.R
import com.example.facerecognition.database.CandidateRepository

class CandidateListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_list)

        val txtCandidates =
            findViewById<TextView>(R.id.txtCandidates)

        val repository =
            CandidateRepository(this)

        val candidates =
            repository.getAllCandidates()

        val builder = StringBuilder()

        for (candidate in candidates) {

            builder.append(
                """
                Name: ${candidate.name}
                Application No: ${candidate.applicationNo}
                Department: ${candidate.department}
                
                -----------------------
                
                """.trimIndent()
            )

            builder.append("\n\n")
        }

        txtCandidates.text = builder.toString()
    }
}