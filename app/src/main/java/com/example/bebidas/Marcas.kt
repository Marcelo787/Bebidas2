package com.example.bebidas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Marcas(
    var nome:String,
    var id: Long = -1
) : Serializable {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaMarcas.CAMPO_NOME, nome)

        return valores
    }
    companion object {
        fun fromCursor(cursor: Cursor) : Marcas {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaMarcas.CAMPO_NOME)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            return Marcas(nome, id)
        }
    }
}