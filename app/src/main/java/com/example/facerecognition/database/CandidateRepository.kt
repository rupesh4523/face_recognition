package com.example.facerecognition.database

import android.content.ContentValues
import android.content.Context
import com.example.facerecognition.models.Candidate

class CandidateRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun insertCandidate(candidate: Candidate): Boolean {

        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put("name", candidate.name)
            put("application_no", candidate.applicationNo)
            put("department", candidate.department)
            put("exam_name", candidate.examName)
            put("image_path", candidate.imagePath)
            put("face_embedding", candidate.faceEmbedding)
        }

        val result = db.insert(
            "Candidate",
            null,
            values
        )

        db.close()

        return result != -1L
    }
    fun getAllCandidates(): List<Candidate> {

        val candidateList = mutableListOf<Candidate>()

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM Candidate",
            null
        )

        if (cursor.moveToFirst()) {

            do {

                val candidate = Candidate(
                    candidateId = cursor.getInt(
                        cursor.getColumnIndexOrThrow("candidate_id")
                    ),
                    name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name")
                    ),
                    applicationNo = cursor.getString(
                        cursor.getColumnIndexOrThrow("application_no")
                    ),
                    department = cursor.getString(
                        cursor.getColumnIndexOrThrow("department")
                    ),
                    examName = cursor.getString(
                        cursor.getColumnIndexOrThrow("exam_name")
                    ),
                    imagePath = cursor.getString(
                        cursor.getColumnIndexOrThrow("image_path")
                    ),
                    faceEmbedding = cursor.getString(
                        cursor.getColumnIndexOrThrow("face_embedding")
                    )
                )

                candidateList.add(candidate)

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return candidateList
    }
    fun getCandidateById(
        candidateId: Int
    ): Candidate? {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM Candidate WHERE candidate_id=?",
            arrayOf(candidateId.toString())
        )

        var candidate: Candidate? = null

        if (cursor.moveToFirst()) {

            candidate = Candidate(
                candidateId = cursor.getInt(
                    cursor.getColumnIndexOrThrow("candidate_id")
                ),
                name = cursor.getString(
                    cursor.getColumnIndexOrThrow("name")
                ),
                applicationNo = cursor.getString(
                    cursor.getColumnIndexOrThrow("application_no")
                ),
                department = cursor.getString(
                    cursor.getColumnIndexOrThrow("department")
                ),
                examName = cursor.getString(
                    cursor.getColumnIndexOrThrow("exam_name")
                ),
                imagePath = cursor.getString(
                    cursor.getColumnIndexOrThrow("image_path")
                ),
                faceEmbedding = cursor.getString(
                    cursor.getColumnIndexOrThrow("face_embedding")
                )
            )
        }

        cursor.close()
        db.close()

        return candidate
    }
    fun deleteCandidate(
        candidateId: Int
    ): Boolean {

        val db = dbHelper.writableDatabase

        val result = db.delete(
            "Candidate",
            "candidate_id=?",
            arrayOf(candidateId.toString())
        )

        db.close()

        return result > 0
    }
    fun updateCandidate(candidate: Candidate): Boolean {

        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put("name", candidate.name)
            put("application_no", candidate.applicationNo)
            put("department", candidate.department)
            put("exam_name", candidate.examName)
            put("image_path", candidate.imagePath)
            put("face_embedding", candidate.faceEmbedding)
        }

        val result = db.update(
            "Candidate",
            values,
            "candidate_id=?",
            arrayOf(candidate.candidateId.toString())
        )

        db.close()

        return result > 0
    }
}