package com.example.form_query

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnShowAll: Button
    private lateinit var tvResults: TextView
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnShowAll = findViewById(R.id.btnShowAll)
        tvResults = findViewById(R.id.tvResults)

        db = AppDatabase(this)

        btnSubmit.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            db.insertUser(name, email)
            etName.text.clear()
            etEmail.text.clear()
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
        }

        btnShowAll.setOnClickListener {
            val users = db.getAllUsers()
            if (users.isEmpty()) {
                tvResults.text = "No records found."
            } else {
                tvResults.text = users.joinToString("\n") { "${it.id}. ${it.name} — ${it.email}" }
            }
        }
    }
}
