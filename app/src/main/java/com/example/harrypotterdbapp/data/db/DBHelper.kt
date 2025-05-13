package com.example.harrypotterdbapp.data.db

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context

class DBHelper(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null , DATABASE_VERSION){

        companion object{
            const val DATABASE_NAME = "harry_potter.db"
            const val DATABASE_VERSION = 1
            const val TABLE_NAME = "hpChars"
        }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                house TEXT NOT NULL,
                ancestry TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}