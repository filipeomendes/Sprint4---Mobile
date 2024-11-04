package com.example.complex

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.complex.control.ProdutoControl
import com.example.complex.model.Produto

class ActivityCadastroProduto : Activity() {
    private lateinit var edtNome: EditText
    private lateinit var edtMarca: EditText
    private lateinit var edtPreco: EditText
    private lateinit var edtQuantidade: EditText
    private val control = ProdutoControl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_produto)

        edtNome = findViewById(R.id.editTextNomeProduto)
        edtMarca = findViewById(R.id.editTextMarca)
        edtPreco = findViewById(R.id.editTextPreco)
        edtQuantidade = findViewById(R.id.editTextQuantidade)

        val btnCadastrar = findViewById<Button>(R.id.buttonCadastrarProduto)
        btnCadastrar.setOnClickListener {
            val nome = edtNome.text.toString()
            val marca = edtMarca.text.toString()
            val precoText = edtPreco.text.toString()
            val quantidadeText = edtQuantidade.text.toString()

            if (nome.isEmpty() || marca.isEmpty() || precoText.isEmpty() || quantidadeText.isEmpty()) {
                Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val preco = precoText.toDoubleOrNull()
            val quantidade = quantidadeText.toIntOrNull()

            if (preco == null || quantidade == null) {
                Toast.makeText(this, "Preço e Quantidade devem ser valores numéricos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val produto = Produto(nome = nome, marca = marca, preco = preco, quantidade = quantidade)

            control.cadastrarProduto(produto,
                sucesso = {
                    Toast.makeText(this, "Produto cadastrado com sucesso", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, ActivityMenu::class.java))
                    finish()
                },
                falha = {
                    Toast.makeText(this, "Erro ao cadastrar produto", Toast.LENGTH_LONG).show()
                }
            )
        }
    }
}