package com.example.complex

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ActivityLogin : Activity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.editTextEmail)
        edtSenha = findViewById(R.id.editTextSenha)

        val btnEntrar = findViewById<Button>(R.id.buttonEntrar)
        btnEntrar.setOnClickListener {
            val email = edtEmail.text.toString()
            val senha = edtSenha.text.toString()

            auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, ActivityMenu::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Falha na autenticação", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        val btnCadastrar = findViewById<Button>(R.id.buttonCadastrar)
        btnCadastrar.setOnClickListener {
            startActivity(Intent(this, ActivityUsuario::class.java))
        }
    }
}