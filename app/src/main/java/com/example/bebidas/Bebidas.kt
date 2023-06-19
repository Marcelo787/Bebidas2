package com.example.bebidas

data class Bebidas(
    var nome: String,
    var descricao: String,
    var idCategoria: Long,
    var id: Long = -1
) {
}