package com.example.complex.control

import com.example.complex.model.Usuario
import com.example.complex.repository.UsuarioRepository

class UsuarioControl {
    private val repository = UsuarioRepository()

    fun registrar(usuario: Usuario, onSucesso: () -> Unit, onFalha: () -> Unit) {
        repository.salvarFirebase(usuario, onSucesso, onFalha)
    }
}