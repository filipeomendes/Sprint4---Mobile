package com.example.complex

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class ActivityMenu : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnCadastrarProduto = findViewById<Button>(R.id.buttonCadastrarProduto)
        btnCadastrarProduto.setOnClickListener {
            startActivity(Intent(this, ActivityCadastroProduto::class.java))
        }

        val btnListaProdutos = findViewById<Button>(R.id.buttonListaProdutos)
        btnListaProdutos.setOnClickListener {
            startActivity(Intent(this, ActivityListaProduto::class.java))
        }
    }
}