package com.example.myapplication

import integracion.TareaDB
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import negocio.Tarea
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

class TareaTest {

    val tarDB = mockk<TareaDB>()

    lateinit var tar : Tarea
    @Before
    fun setUp(){
        tar = Tarea()
    }
    @Test
    fun test_Tarea_Existe() = runBlocking {
        coEvery { tarDB.existe(tar) } returns true
        assertTrue(tarDB.existe(tar))
    }

    @Test
    fun test_No_Tarea_Existe() = runBlocking {
        coEvery { tarDB.existe(tar) } returns false
        assertFalse(tarDB.existe(tar))
    }
    //TODO borrar luego
    @Test
    fun test_Tarea_Guardar(){
        coEvery { tarDB.guardar(tar) } returns true;
        assertTrue(tarDB.guardar(tar))
    }

    @Test
    fun test_No_Tarea_Guardar(){
        coEvery { tarDB.guardar(tar) } returns false;
        assertFalse(tarDB.guardar(tar))
    }
}