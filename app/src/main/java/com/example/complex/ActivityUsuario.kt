package com.example.complex

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.complex.control.UsuarioControl
import com.example.complex.model.Usuario

class ActivityUsuario : Activity() {
    private val control = UsuarioControl()
    private lateinit var edtNome: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtTelefone: EditText
    private lateinit var edtSenha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)

        edtNome = findViewById(R.id.editTextNome)
        edtEmail = findViewById(R.id.editTextEmail)
        edtTelefone = findViewById(R.id.editTextTelefone)
        edtSenha = findViewById(R.id.editTextSenha)

        val btnRegistrar = findViewById<Button>(R.id.buttonRegistrar)
        btnRegistrar.setOnClickListener {
            val usuario = Usuario(
                nome = edtNome.text.toString(),
                email = edtEmail.text.toString(),
                telefone = edtTelefone.text.toString(),
                senha = edtSenha.text.toString()
            )

            control.registrar(usuario,
                onSucesso = {
                    Toast.makeText(this, "Usuário registrado com sucesso", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, ActivityLogin::class.java))
                    finish()
                },
                onFalha = {
                    Toast.makeText(this, "Erro ao registrar usuário", Toast.LENGTH_LONG).show()
                }
            )
        }
    }
}