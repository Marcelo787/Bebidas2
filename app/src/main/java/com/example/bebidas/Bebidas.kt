package com.example.bebidas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns


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


    companion object {
        fun fromCursor(cursor: Cursor) : Bebidas {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBebidas.CAMPO_NOME)
            val posDescricao = cursor.getColumnIndex(TabelaBebidas.CAMPO_DESCRICAO)
            val posBebidasFK = cursor.getColumnIndex(TabelaBebidas.CAMPO_FK_MARCA)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val descricao = cursor.getString(posDescricao)
            val bebidaId = cursor.getLong(posBebidasFK)

            return Bebidas(nome,descricao,bebidaId, id)
        }
    }
}