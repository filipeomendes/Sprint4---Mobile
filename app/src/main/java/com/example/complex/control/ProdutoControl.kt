package com.example.complex.control

import com.example.complex.model.Produto
import com.example.complex.repository.ProdutoRepository

class ProdutoControl {

    private val produtoRepository = ProdutoRepository()

    fun cadastrarProduto(
        produto: Produto,
        sucesso: () -> Unit,
        falha: () -> Unit
    ) {
        produtoRepository.salvarFirebase(produto, {
            sucesso()
        }, {
            falha()
        })
    }

    fun atualizarProduto(
        id: Int,
        produto: Produto,
        sucesso: () -> Unit,
        falha: () -> Unit
    ) {
        produtoRepository.atualizarProduto(id = "", produto, {
            sucesso()
        }, {
            falha()
        })
    }

    fun excluirProduto(
        id: Int,
        sucesso: () -> Unit,
        falha: () -> Unit
    ) {
        produtoRepository.excluirProduto(id = "", {
            sucesso()
        }, {
            falha()
        })
    }

    fun listarProdutos(
        sucesso: (List<Produto>) -> Unit,
        falha: () -> Unit
    ) {
        produtoRepository.listarTodosProdutos({
            sucesso(it)
        }, {
            falha()
        })
    }
}