package com.example.complex.repository

import android.util.Log
import com.example.complex.model.Usuario
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class UsuarioRepository {
    private val httpClient = OkHttpClient()
    private val gson = Gson()
    private val URL_BASE = "https://sprint4-8f85f-default-rtdb.firebaseio.com/"
    private val contadorUrl = "$URL_BASE/contador.json"

    fun salvarFirebase(usuario: Usuario, sucesso: () -> Unit, falha: () -> Unit) {
        obterId { novoId ->
            val usuarioJson = gson.toJson(usuario)
            val request = Request.Builder()
                .url("$URL_BASE/usuarios/$novoId.json")
                .put(usuarioJson.toRequestBody("application/json".toMediaType()))
                .build()

            httpClient.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        Log.i("USUARIO", "Usuário registrado com sucesso")
                        sucesso()
                    } else {
                        Log.e("USUARIO", "Erro ao registrar usuário: ${response.message}")
                        falha()
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.e("USUARIO", "Erro ao registrar usuário: ${e.message}", e)
                    falha()
                }
            })
        }
    }

    private fun obterId(callback: (Int) -> Unit) {
        val request = Request.Builder()
            .url(contadorUrl)
            .get()
            .build()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val contadorJson = response.body?.string()
                    val contador = gson.fromJson(contadorJson, Int::class.java) ?: 1
                    callback(contador)
                    atualizarContador(contador + 1)
                } else {
                    Log.e("USUARIO", "Erro ao obter contador: ${response.message}")
                    callback(1)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("USUARIO", "Erro ao acessar contador: ${e.message}", e)
                callback(1)
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
                    Log.e("USUARIO", "Erro ao atualizar contador: ${response.message}")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("USUARIO", "Erro ao atualizar contador: ${e.message}", e)
            }
        })
    }
}