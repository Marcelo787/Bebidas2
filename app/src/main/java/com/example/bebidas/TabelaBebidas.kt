package com.example.bebidas

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBebidas(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOME TEXT NOT NULL, $CAMPO_DESCRICAO TEXT, $CAMPO_FK_MARCA INTEGER NOT NULL, FOREIGN KEY ($CAMPO_FK_MARCA) REFERENCES ${TabelaMarcas.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object {
        const val NOME_TABELA = "bebida"
        const val CAMPO_NOME = "nome_bebida"
        const val CAMPO_DESCRICAO = "descricao_da_bebida"
        const val CAMPO_FK_MARCA = "id_marca"
    }
}