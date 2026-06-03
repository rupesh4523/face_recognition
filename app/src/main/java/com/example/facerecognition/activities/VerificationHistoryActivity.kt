package com.example.facerecognition.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facerecognition.R
import com.example.facerecognition.adapters.VerificationLogAdapter
import com.example.facerecognition.database.VerificationLogRepository

class VerificationHistoryActivity :
    AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_verification_history
        )

        val recyclerLogs =
            findViewById<RecyclerView>(
                R.id.recyclerLogs
            )

        val btnBack =
            findViewById<Button>(
                R.id.btnBack
            )

        val repository =
            VerificationLogRepository(this)

        val logs =
            repository.getAllLogs()

        recyclerLogs.layoutManager =
            LinearLayoutManager(this)

        recyclerLogs.adapter =
            VerificationLogAdapter(logs)

        btnBack.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    CandidateVerificationActivity::class.java
                )
            )

            finish()
        }
    }
}