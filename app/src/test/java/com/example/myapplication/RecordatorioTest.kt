package com.example.myapplication

import integracion.RecordatorioDB
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import negocio.Recordatorio
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*


class RecordatorioTest {
    var rDB = mockk<RecordatorioDB>()
    lateinit var record: Recordatorio;
    @Before
    fun setUp(){
        var fecha = Calendar.getInstance()
        fecha.set(2023, 5, 8,18, 30,0)
        record = Recordatorio("Nombre", "Categoria", "Descripcion", fecha)
    }
    @Test
    fun test_Rec_Existe() = runBlocking {
        coEvery { rDB.existe(record) } returns true
        Assert.assertTrue(rDB.existe(record))
    }

    @Test
    fun test_No_Rec_Existe() = runBlocking {
        coEvery { rDB.existe(record) } returns false
        Assert.assertFalse(rDB.existe(record))
    }

    @Test
    fun test_Rec_Guardar() {
        coEvery { rDB.guardar(record) } returns true;
        Assert.assertTrue(rDB.guardar(record))
    }

    @Test
    fun test_No_Rec_Guardar() {
        coEvery { rDB.guardar(record) } returns false;
        Assert.assertFalse(rDB.guardar(record))
    }

    @Test
    fun test_No_Null_Recordatorio_Lista_Tareas() = runBlocking{
        coEvery { rDB.listarTodas()} returns ArrayList<Recordatorio>()
        val resultList = rDB.listarTodas()
        Assert.assertNotNull(resultList)
    }

    @Test
    fun test_Recordatorio_Lista_Tareas() = runBlocking{
        coEvery { rDB.listarTodas()} returns ArrayList<Recordatorio>()
        val resultList = rDB.listarTodas()
        val lista = rDB.listarTodas()
        assertEquals(resultList, lista)
    }


}