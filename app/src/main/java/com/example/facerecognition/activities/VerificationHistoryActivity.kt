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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.io.File
import java.io.FileWriter
import android.os.Environment
import android.widget.TextView

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

        val btnClearHistory =
            findViewById<Button>(
                R.id.btnClearHistory
            )

        val btnExportLogs =
            findViewById<Button>(
                R.id.btnExportLogs
            )

        val repository =
            VerificationLogRepository(this)

        val logs =
            repository.getAllLogs()

        val txtHistoryCount =
            findViewById<TextView>(
                R.id.txtHistoryCount
            )

        txtHistoryCount.text =
            "Total Records : ${logs.size}"

        recyclerLogs.layoutManager =
            LinearLayoutManager(this)

        recyclerLogs.adapter =
            VerificationLogAdapter(logs)

        btnExportLogs.setOnClickListener {

            try {
                val fileName =
                    "verification_logs_" +
                            System.currentTimeMillis() +
                            ".csv"

                val file =
                    File(
                        Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS
                        ),
                        fileName
                    )

                val writer =
                    FileWriter(file)

                writer.append(
                    "Name,ApplicationNo,Result,Confidence,DateTime\n"
                )

                logs.forEach { log ->

                    writer.append(
                        "${log.candidateName}," +
                                "${log.applicationNo}," +
                                "${log.result}," +
                                "${log.confidence}," +
                                "${log.dateTime}\n"
                    )
                }

                writer.flush()
                writer.close()

                Toast.makeText(
                    this,
                    "CSV Saved:\n${file.absolutePath}",
                    Toast.LENGTH_LONG
                ).show()

            } catch (e: Exception) {

                Toast.makeText(
                    this,
                    "Export Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnClearHistory.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Clear History")
                .setMessage(
                    "Delete all verification logs?"
                )
                .setPositiveButton("Yes") { _, _ ->

                    repository.clearAllLogs()

                    Toast.makeText(
                        this,
                        "History Cleared",
                        Toast.LENGTH_SHORT
                    ).show()

                    recreate()
                }
                .setNegativeButton(
                    "No",
                    null
                )
                .show()
        }

        btnBack.setOnClickListener {

            finish()
        }
    }
}