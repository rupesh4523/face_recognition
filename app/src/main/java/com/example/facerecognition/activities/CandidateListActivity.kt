package com.example.facerecognition.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facerecognition.R
import com.example.facerecognition.adapters.CandidateAdapter
import com.example.facerecognition.database.CandidateRepository

class CandidateListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_candidate_list)

        val recyclerCandidates =
            findViewById<RecyclerView>(
                R.id.recyclerCandidates
            )

        val etSearch =
            findViewById<EditText>(
                R.id.etSearch
            )

        val repository =
            CandidateRepository(this)

        val candidateList =
            repository.getAllCandidates()

        val originalList =
            candidateList.toMutableList()

        recyclerCandidates.layoutManager =
            LinearLayoutManager(this)

        val adapter =
            CandidateAdapter(
                candidateList.toMutableList()
            ) { candidate ->

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

        recyclerCandidates.adapter =
            adapter

        etSearch.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    val query =
                        s.toString().lowercase()

                    val filteredList =
                        originalList.filter {

                            it.name.lowercase()
                                .contains(query)

                                    ||

                                    it.applicationNo.lowercase()
                                        .contains(query)
                        }

                    adapter.updateList(
                        filteredList
                    )
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {}
            }
        )
    }
}