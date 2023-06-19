package com.example.bebidas
import android.database.sqlite.SQLiteDatabase


class TabelaMarcas(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOME TEXT NOT NULL)")
    }

    companion object {
        const val NOME_TABELA = "marcas"
        const val CAMPO_NOME = "nome_da_marca"
    }
}