package com.example.form_query

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppDatabase(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "form_query.db"
        const val DB_VERSION = 1
        const val TABLE = "users"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "email TEXT NOT NULL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(db)
    }

    fun insertUser(name: String, email: String): Long {
        val values = ContentValues().apply {
            put("name", name)
            put("email", email)
        }
        return writableDatabase.insert(TABLE, null, values)
    }

    fun getAllUsers(): List<User> {
        val users = mutableListOf<User>()
        val cursor = readableDatabase.rawQuery("SELECT * FROM $TABLE", null)
        cursor.use {
            while (it.moveToNext()) {
                users.add(
                    User(
                        id = it.getInt(it.getColumnIndexOrThrow("id")),
                        name = it.getString(it.getColumnIndexOrThrow("name")),
                        email = it.getString(it.getColumnIndexOrThrow("email"))
                    )
                )
            }
        }
        return users
    }
}
