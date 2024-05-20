package com.example.loginregisterapp

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        dbHelper = DatabaseHelper(this)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val tvSex = findViewById<TextView>(R.id.tvSex)
        val tvAge = findViewById<TextView>(R.id.tvAge)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        val username = intent.getStringExtra("username")

        val cursor = dbHelper.getUserDetails(username!!)
        if (cursor != null && cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val sex = cursor.getString(cursor.getColumnIndexOrThrow("sex"))
            val year = cursor.getInt(cursor.getColumnIndexOrThrow("year"))
            val age = 2024 - year // Adjust the calculation based on the current year

            tvWelcome.text = "Welcome, $name"
            tvSex.text = "Sex: $sex"
            tvAge.text = "Age: $age"
        }
        cursor?.close()

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
