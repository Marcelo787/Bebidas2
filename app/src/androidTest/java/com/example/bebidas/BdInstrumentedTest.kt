package com.example.bebidas

import android.content.Context
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

    @Test
    fun consegueInserirMarcas() {
        val openHelper = BdBebidasOpenHelper(getAppContext())
        val bd = openHelper.writableDatabase

        val marca = Marcas("Super Bock")
        val id = TabelaMarcas(bd).insere(marca.toContentValues())
        assertNotEquals(-1, id)
    }

}