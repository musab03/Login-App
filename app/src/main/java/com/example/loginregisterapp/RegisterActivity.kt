package com.example.loginregisterapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = DatabaseHelper(this)

        val etName = findViewById<EditText>(R.id.etName)
        val rgSex = findViewById<RadioGroup>(R.id.rgSex)
        val etYear = findViewById<EditText>(R.id.etYear)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etRePassword = findViewById<EditText>(R.id.etRePassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val btnGoBack = findViewById<Button>(R.id.btnGoBack)

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val sex = findViewById<RadioButton>(rgSex.checkedRadioButtonId)?.text.toString()
            val year = etYear.text.toString().toIntOrNull()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val rePassword = etRePassword.text.toString()

            if (validateRegistration(name, sex, year, username, password, rePassword)) {
                if (dbHelper.isUsernameTaken(username)) {
                    Toast.makeText(this, "Username already taken", Toast.LENGTH_SHORT).show()
                } else {
                    val isAdded = dbHelper.addUser(name, sex, year!!, username, password)
                    if (isAdded) {
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                        finish()  // Go back to the login screen after successful registration
                    } else {
                        Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
            }
        }

        btnClear.setOnClickListener {
            etName.text.clear()
            rgSex.clearCheck()
            etYear.text.clear()
            etUsername.text.clear()
            etPassword.text.clear()
            etRePassword.text.clear()
        }

        btnGoBack.setOnClickListener {
            finish()
        }
    }

    private fun validateRegistration(name: String, sex: String?, year: Int?, username: String, password: String, rePassword: String): Boolean {
        if (name.isEmpty() || sex.isNullOrEmpty() || year == null || username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != rePassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
