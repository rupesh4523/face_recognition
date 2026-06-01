package com.example.facerecognition.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {

    companion object {
        private const val DATABASE_NAME = "CandidateVerification.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {

        val createAdminTable = """
            CREATE TABLE Admin (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL,
                password TEXT NOT NULL
            )
        """.trimIndent()

        val createCandidateTable = """
            CREATE TABLE Candidate (
                candidate_id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                application_no TEXT NOT NULL,
                department TEXT NOT NULL,
                exam_name TEXT NOT NULL,
                image_path TEXT,
                face_embedding TEXT
            )
        """.trimIndent()

        val createVerificationTable = """
            CREATE TABLE VerificationLog (
                verification_id INTEGER PRIMARY KEY AUTOINCREMENT,
                candidate_id INTEGER,
                date TEXT,
                time TEXT,
                liveness_status TEXT,
                verification_status TEXT
            )
        """.trimIndent()

        db.execSQL(createAdminTable)
        db.execSQL(createCandidateTable)
        db.execSQL(createVerificationTable)

        db.execSQL(
            """
            INSERT INTO Admin(username,password)
            VALUES('admin','admin123')
            """
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

        db.execSQL("DROP TABLE IF EXISTS Admin")
        db.execSQL("DROP TABLE IF EXISTS Candidate")
        db.execSQL("DROP TABLE IF EXISTS VerificationLog")

        onCreate(db)
    }
}