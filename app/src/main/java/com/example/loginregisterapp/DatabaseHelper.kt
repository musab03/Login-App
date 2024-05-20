package com.example.loginregisterapp


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_SEX = "sex"
        private const val COLUMN_YEAR = "year"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_SEX TEXT, " +
                "$COLUMN_YEAR INTEGER, " +
                "$COLUMN_USERNAME TEXT, " +
                "$COLUMN_PASSWORD TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addUser(name: String, sex: String, year: Int, username: String, password: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, name)
        contentValues.put(COLUMN_SEX, sex)
        contentValues.put(COLUMN_YEAR, year)
        contentValues.put(COLUMN_USERNAME, username)
        contentValues.put(COLUMN_PASSWORD, password)
        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result != -1L
    }

    fun getUser(username: String, password: String): Cursor? {
        val db = this.readableDatabase
        return db.query(TABLE_NAME, null, "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?", arrayOf(username, password), null, null, null)
    }

    fun isUsernameTaken(username: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, null, "$COLUMN_USERNAME = ?", arrayOf(username), null, null, null)
        val isTaken = cursor.count > 0
        cursor.close()
        db.close()
        return isTaken
    }

    fun getUserDetails(username: String): Cursor? {
        val db = this.readableDatabase
        return db.query(TABLE_NAME, null, "$COLUMN_USERNAME = ?", arrayOf(username), null, null, null)
    }
}
