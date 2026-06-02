package com.example.facerecognition.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facerecognition.R
import com.example.facerecognition.adapters.CandidateAdapter
import com.example.facerecognition.database.CandidateRepository
import android.content.Intent

class CandidateListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_list)

        val recyclerCandidates =
            findViewById<RecyclerView>(
                R.id.recyclerCandidates
            )

        val repository =
            CandidateRepository(this)

        val candidateList =
            repository.getAllCandidates()

        recyclerCandidates.layoutManager =
            LinearLayoutManager(this)

        recyclerCandidates.adapter =
            CandidateAdapter(candidateList) { candidate ->

                val intent =
                    Intent(
                        this,
                        CandidateProfileActivity::class.java
                    )

                intent.putExtra(
                    "CANDIDATE_ID",
                    candidate.candidateId
                )

                startActivity(intent)
            }
    }
}