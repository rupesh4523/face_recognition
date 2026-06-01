package com.example.facerecognition.database

import android.content.Context
import android.database.Cursor

class AdminRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun login(username: String, password: String): Boolean {

        val db = dbHelper.readableDatabase

        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM Admin WHERE username=? AND password=?",
            arrayOf(username, password)
        )

        val exists = cursor.count > 0

        cursor.close()
        db.close()

        return exists
    }
}