package com.example.facerecognition.database

import android.content.ContentValues
import android.content.Context
import com.example.facerecognition.models.VerificationLog

class VerificationLogRepository(
    context: Context
) {

    private val dbHelper =
        DatabaseHelper(context)

    fun insertLog(
        log: VerificationLog
    ): Boolean {

        val db =
            dbHelper.writableDatabase

        val values =
            ContentValues().apply {

                put(
                    "candidate_name",
                    log.candidateName
                )

                put(
                    "application_no",
                    log.applicationNo
                )

                put(
                    "date_time",
                    log.dateTime
                )

                put(
                    "result",
                    log.result
                )

                put(
                    "confidence",
                    log.confidence
                )
            }

        val result =
            db.insert(
                "VerificationLog",
                null,
                values
            )

        db.close()

        return result != -1L
    }

    fun getAllLogs():
            List<VerificationLog> {

        val logList =
            mutableListOf<VerificationLog>()

        val db =
            dbHelper.readableDatabase

        val cursor =
            db.rawQuery(
                """
                SELECT * FROM VerificationLog
                ORDER BY verification_id DESC
                """,
                null
            )

        if (cursor.moveToFirst()) {

            do {

                val log =
                    VerificationLog(

                        verificationId =
                            cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                    "verification_id"
                                )
                            ),

                        candidateName =
                            cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                    "candidate_name"
                                )
                            ),

                        applicationNo =
                            cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                    "application_no"
                                )
                            ),

                        dateTime =
                            cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                    "date_time"
                                )
                            ),

                        result =
                            cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                    "result"
                                )
                            ),

                        confidence =
                            cursor.getFloat(
                                cursor.getColumnIndexOrThrow(
                                    "confidence"
                                )
                            )
                    )

                logList.add(log)

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return logList
    }
    fun clearAllLogs(): Boolean {

        val db =
            dbHelper.writableDatabase

        val result =
            db.delete(
                "VerificationLog",
                null,
                null
            )

        db.close()

        return result >= 0
    }

    fun getTotalVerifications(): Int {

        val db = dbHelper.readableDatabase

        val cursor =
            db.rawQuery(
                "SELECT COUNT(*) FROM VerificationLog",
                null
            )

        cursor.moveToFirst()

        val count =
            cursor.getInt(0)

        cursor.close()
        db.close()

        return count
    }

    fun getSuccessfulMatches(): Int {

        val db = dbHelper.readableDatabase

        val cursor =
            db.rawQuery(
                """
            SELECT COUNT(*)
            FROM VerificationLog
            WHERE result='MATCHED'
            """,
                null
            )

        cursor.moveToFirst()

        val count =
            cursor.getInt(0)

        cursor.close()
        db.close()

        return count
    }

    fun getFailedMatches(): Int {

        val db = dbHelper.readableDatabase

        val cursor =
            db.rawQuery(
                """
            SELECT COUNT(*)
            FROM VerificationLog
            WHERE result='NOT MATCHED'
            """,
                null
            )

        cursor.moveToFirst()

        val count =
            cursor.getInt(0)

        cursor.close()
        db.close()

        return count
    }
}