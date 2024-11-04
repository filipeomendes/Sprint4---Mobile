package com.example.complex

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.complex.control.ProdutoControl
import com.example.complex.model.Produto

class ActivityListaProduto : Activity() {
    private lateinit var listView: ListView
    private val control = ProdutoControl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_produto)

        listView = findViewById(R.id.listViewProdutos)

        control.listarProdutos(
            sucesso = { produtos ->
                val adapter = object : ArrayAdapter<Produto>(this, R.layout.item_produto, produtos) {
                    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                        val view = convertView ?: layoutInflater.inflate(R.layout.item_produto, parent, false)
                        val produto = getItem(position)

                        val textViewNomeProduto = view.findViewById<TextView>(R.id.textViewNomeProduto)
                        val buttonAtualizar = view.findViewById<Button>(R.id.buttonAtualizar)
                        val buttonExcluir = view.findViewById<Button>(R.id.buttonExcluir)

                        textViewNomeProduto.text = produto?.nome

                        buttonAtualizar.setOnClickListener {
                            produto?.let {
                                control.atualizarProduto(it.id, it,
                                    sucesso = { Toast.makeText(this@ActivityListaProduto, "Produto atualizado", Toast.LENGTH_SHORT).show() },
                                    falha = { Toast.makeText(this@ActivityListaProduto, "Erro ao atualizar", Toast.LENGTH_SHORT).show() }
                                )
                            }
                        }

                        buttonExcluir.setOnClickListener {
                            produto?.let {
                                control.excluirProduto(it.id,
                                    sucesso = { Toast.makeText(this@ActivityListaProduto, "Produto excluído", Toast.LENGTH_SHORT).show() },
                                    falha = { Toast.makeText(this@ActivityListaProduto, "Erro ao excluir", Toast.LENGTH_SHORT).show() }
                                )
                            }
                        }

                        return view
                    }
                }

                listView.adapter = adapter
            },
            falha = {
                Toast.makeText(this, "Erro ao carregar a lista de produtos", Toast.LENGTH_LONG).show()
            }
        )
    }
}