package com.example.bebidas

import android.content.ContentValues

data class Bebidas(
    var nome: String,
    var descricao: String,
    var idMarca: Long,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBebidas.CAMPO_NOME, nome)
        valores.put(TabelaBebidas.CAMPO_DESCRICAO, descricao)
        valores.put(TabelaBebidas.CAMPO_FK_MARCA, idMarca)

        return valores
    }
}