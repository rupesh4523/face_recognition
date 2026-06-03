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

    fun getAdmin(): Pair<String, String>? {

        val db =
            dbHelper.readableDatabase

        val cursor =
            db.rawQuery(
                "SELECT username,password FROM Admin LIMIT 1",
                null
            )

        var admin: Pair<String, String>? = null

        if (cursor.moveToFirst()) {

            admin =
                Pair(
                    cursor.getString(0),
                    cursor.getString(1)
                )
        }

        cursor.close()
        db.close()

        return admin
    }

    fun updateAdmin(
        username: String,
        password: String
    ): Boolean {

        val db =
            dbHelper.writableDatabase

        val values =
            android.content.ContentValues()

        values.put(
            "username",
            username
        )

        values.put(
            "password",
            password
        )

        val result =
            db.update(
                "Admin",
                values,
                "id=1",
                null
            )

        db.close()

        return result > 0
    }
}