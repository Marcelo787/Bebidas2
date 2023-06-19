package com.example.bebidas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BdInstrumentedTest {
    private fun getAppContext(): Context =
        InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase(BdBebidasOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BdBebidasOpenHelper(getAppContext())
        val bd = openHelper.readableDatabase
        assert(bd.isOpen)
    }

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BdBebidasOpenHelper(getAppContext())
        return openHelper.writableDatabase
    }


    @Test
    fun consegueInserirMarcas() {
        val bd = getWritableDatabase()

        val marca = Marcas("Super Bock")
        insereMarca(bd, marca)
    }

    private fun insereMarca(
        bd: SQLiteDatabase,
        marcas: Marcas
    ) {
        marcas.id = TabelaMarcas(bd).insere(marcas.toContentValues())
        assertNotEquals(-1, marcas.id)
    }

    @Test
    fun consegueInserirBebidas() {
        val bd = getWritableDatabase()

        val marcas = Marcas("Heineken")
        insereMarca(bd, marcas)

        val bebidas1 = Bebidas("Cerveja com Álcool Mini","Contém Cereais que contêm glúten", marcas.id)
        insereBebidas(bd, bebidas1)

        val bebidas2 = Bebidas("Cerveja com Álcool Barril","Contém Cereais que contêm glúten", marcas.id, )
        insereBebidas(bd, bebidas2)
    }

    private fun insereBebidas(bd: SQLiteDatabase, bebidas: Bebidas) {
        bebidas.id = TabelaBebidas(bd).insere(bebidas.toContentValues())
        assertNotEquals(-1, bebidas.id)
    }

    @Test
    fun consegueLerMarcas() {
        val bd = getWritableDatabase()

        val marca1 = Marcas("Super Bock")
        insereMarca(bd, marca1)

        val marca2 = Marcas("Sagres")
        insereMarca(bd, marca2)

        val tabelaMarcas = TabelaMarcas(bd)

        val cursor = tabelaMarcas.consulta(
            TabelaMarcas.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(marca1.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val marcaBD = Marcas.fromCursor(cursor)

        assertEquals(marca1, marcaBD)

        val cursorTodasMarcas = tabelaMarcas.consulta(
            TabelaMarcas.CAMPOS,
            null, null, null, null,
            TabelaMarcas.CAMPO_NOME
        )

        assert(cursorTodasMarcas.count > 1)
    }
}