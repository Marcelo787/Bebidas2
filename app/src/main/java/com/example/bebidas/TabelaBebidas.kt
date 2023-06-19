package com.example.bebidas

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBebidas(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, nome TEXT NOT NULL, descricao TEXT, id_marca INTEGER NOT NULL, FOREIGN KEY (id_marca) REFERENCES ${TabelaMarcas.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object {
        const val NOME_TABELA = "livros"
    }
}