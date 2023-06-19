package com.example.bebidas

import android.content.ContentValues

data class Marcas(
    var nome:String,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaMarcas.CAMPO_NOME, nome)

        return valores
    }
}