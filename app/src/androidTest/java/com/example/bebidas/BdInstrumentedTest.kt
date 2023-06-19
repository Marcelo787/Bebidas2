package com.example.bebidas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.*

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


    @Test
    fun consegueLerBebidas() {
        val bd = getWritableDatabase()

        val marca = Marcas("Gutbier")
        insereMarca(bd, marca)

        val bebida1 = Bebidas("Cerveja com Álcool", " É uma cerveja fácil de beber, leve, com um moderado conteúdo alcoólico e com um amargor refrescante ao paladar. ",marca.id)
        insereBebidas(bd, bebida1)

        val bebida2 = Bebidas("Cerveja sem Álcool", " É uma cerveja fácil de beber, leve, com um moderado conteúdo alcoólico e com um amargor refrescante ao paladar. ", marca.id, )
        insereBebidas(bd, bebida2)

        val tabelaBebidas = TabelaBebidas(bd)

        val cursor = tabelaBebidas.consulta(
            TabelaBebidas.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(bebida1.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val bebidasBD = Bebidas.fromCursor(cursor)

        assertEquals(bebida1, bebidasBD)

        val cursorTodosBebidas = tabelaBebidas.consulta(
            TabelaBebidas.CAMPOS,
            null, null, null, null,
            TabelaBebidas.CAMPO_NOME
        )

        assert(cursorTodosBebidas.count > 1)
    }


    @Test
    fun consegueAlterarMarcas() {
        val bd = getWritableDatabase()

        val marcas = Marcas("Cristal")
        insereMarca(bd, marcas)

        marcas.nome = "Poesia"

        val registosAlterados = TabelaMarcas(bd).altera(
            marcas.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(marcas.id.toString())
        )

        assertEquals(1, registosAlterados)
    }

    @Test
    fun consegueAlterarBebidas() {
        val bd = getWritableDatabase()

        val marcas1 = Marcas("Somersby")
        insereMarca(bd, marcas1)

        val marcas2 = Marcas("Bandida do Pomar")
        insereMarca(bd, marcas2)

        val bebidas = Bebidas("Sidra com Álcool Maçã Mini","Teor alcoólico: 4,5%", marcas2.id)
        insereBebidas(bd, bebidas)

        bebidas.idMarca = marcas1.id
        bebidas.nome = "Sidra com Álcool Maçã"
        bebidas.descricao = "Teor alcoólico: 4,2%"

        val registosAlterados = TabelaBebidas(bd).altera(
            bebidas.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(bebidas.id.toString())
        )

        assertEquals(1, registosAlterados)
    }

    @Test
    fun consegueApagarMarcas() {
        val bd = getWritableDatabase()

        val marcas = Marcas("Coruja")
        insereMarca(bd, marcas)

        val registosEliminados = TabelaMarcas(bd).elimina(
            "${BaseColumns._ID}=?",
            arrayOf(marcas.id.toString())
        )

        assertEquals(1, registosEliminados)
    }

    @Test
    fun consegueApagarBebidas() {
        val bd = getWritableDatabase()

        val marcas = Marcas("Franziskaner")
        insereMarca(bd, marcas)

        val bebidas = Bebidas("Cerveja com Álcool", "Percentagem de álcool em volume: 5",marcas.id)
        insereBebidas(bd, bebidas)

        val registosEliminados = TabelaBebidas(bd).elimina(
            "${BaseColumns._ID}=?",
            arrayOf(bebidas.id.toString())
        )

        assertEquals(1, registosEliminados)
    }
}