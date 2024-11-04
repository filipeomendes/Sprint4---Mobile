package com.example.complex.repository

import android.util.Log
import com.example.complex.model.Produto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class ProdutoRepository {
    private val httpClient = OkHttpClient()
    private val gson = Gson()
    private val URL_BASE = "https://sprint4-8f85f-default-rtdb.firebaseio.com/"
    private val contadorUrl = "$URL_BASE/contador.json"

    fun salvarFirebase(produto: Produto, sucesso: () -> Unit, falha: () -> Unit) {
        obterId { novoId ->
            val produtoJson = gson.toJson(produto)
            val request = Request.Builder()
                .url("$URL_BASE/produtos/$novoId.json")
                .put(produtoJson.toRequestBody("application/json".toMediaType()))
                .build()

            httpClient.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        Log.i("PRODUTO", "Produto cadastrado com sucesso")
                        sucesso()
                    } else {
                        Log.e("PRODUTO", "Erro ao cadastrar produto: ${response.message}")
                        falha()
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.e("PRODUTO", "Erro ao registrar produto: ${e.message}", e)
                    falha()
                }
            })
        }
    }

    fun atualizarProduto(id: String, produto: Produto, sucesso: () -> Unit, falha: () -> Unit) {
        val produtoJson = gson.toJson(produto)
        val request = Request.Builder()
            .url("$URL_BASE/produtos/$id.json")
            .put(produtoJson.toRequestBody("application/json".toMediaType()))
            .build()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.i("PRODUTO", "Produto atualizado com sucesso")
                    sucesso()
                } else {
                    Log.e("PRODUTO", "Erro ao atualizar produto: ${response.message}")
                    falha()
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("PRODUTO", "Erro ao atualizar produto: ${e.message}", e)
                falha()
            }
        })
    }

    fun excluirProduto(id: String, sucesso: () -> Unit, falha: () -> Unit) {
        val request = Request.Builder()
            .url("$URL_BASE/produtos/$id.json")
            .delete()
            .build()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.i("PRODUTO", "Produto excluído com sucesso")
                    sucesso()
                } else {
                    Log.e("PRODUTO", "Erro ao excluir produto: ${response.message}")
                    falha()
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("PRODUTO", "Erro ao excluir produto: ${e.message}", e)
                falha()
            }
        })
    }

    private fun obterId(callback: (String) -> Unit) {
        val request = Request.Builder()
            .url(contadorUrl)
            .get()
            .build()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val contadorJson = response.body?.string()
                    val contador = gson.fromJson(contadorJson, Int::class.java) ?: 1
                    callback(contador.toString()) // Alterado para String
                    atualizarContador(contador + 1)
                } else {
                    Log.e("PRODUTO", "Erro ao obter contador: ${response.message}")
                    callback("1") // Alterado para String
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("PRODUTO", "Erro ao acessar contador: ${e.message}", e)
                callback("1") // Alterado para String
            }
        })
    }

    private fun atualizarContador(novoContador: Int) {
        val requestBody = novoContador.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(contadorUrl)
            .put(requestBody)
            .build()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    Log.e("PRODUTO", "Erro ao atualizar contador: ${response.message}")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("PRODUTO", "Erro ao atualizar contador: ${e.message}", e)
            }
        })
    }

    fun listarTodosProdutos(sucesso: (List<Produto>) -> Unit, falha: () -> Unit) {
        val request = Request.Builder()
            .url("$URL_BASE/produtos.json")
            .get()
            .build()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val produtosJson = response.body?.string()
                    val produtosType = object : TypeToken<Map<String, Produto>>() {}.type
                    val produtosMap: Map<String, Produto> = gson.fromJson(produtosJson, produtosType)
                    val produtosList = produtosMap.values.toList()
                    sucesso(produtosList)
                } else {
                    Log.e("PRODUTO", "Erro ao listar produtos: ${response.message}")
                    falha()
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("PRODUTO", "Erro ao acessar produtos: ${e.message}", e)
                falha()
            }
        })
    }
}