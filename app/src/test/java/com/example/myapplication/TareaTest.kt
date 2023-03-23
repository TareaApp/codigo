package com.example.myapplication

import android.util.Log
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
import java.util.Calendar

class TareaTest {

    val tDB = mockk<TareaDB>()

    lateinit var tar: Tarea
    lateinit var tar1: Tarea
    lateinit var tar2: Tarea
    lateinit var tar3: Tarea
    var lista = mutableListOf<Tarea>()

    @Before
    fun setUp() {
        var c = Calendar.getInstance()
        c.set(2023,4,10,13,10);
        tar = Tarea("Tar", "Asig", 0, 20, "Original",c)

        var c1 = Calendar.getInstance()
        var c2 = Calendar.getInstance()
        var c3 = Calendar.getInstance()

        c1.set(2023,4,10,12,25)
        tar1 = Tarea("Tar1", "Asig1", 1, 20, "1º",c1)

        c2.set(2023,4,10,14,0)
        tar2 = Tarea("Tar2", "Asig2", 0, 30, "2º",c2)


        c3.set(2023,4,11,17,30)
        tar3 = Tarea("Tar3", "Asig3", 1, 0, "3º",c3)
        lista.add(tar1)
        lista.add(tar2)
        lista.add(tar3)
    }

    @Test
    fun setPlanNull() = runBlocking {
        tar.setDB(tDB)
        coEvery { tDB.tareasPosteriores(any()) } returns lista
        tar.setPlanNull()
        var fecha = tar.getPlan()?.time
        assertEquals(fecha.toString(), "hola")
    }

    @Test
    fun test_Tarea_Existe() = runBlocking {
        coEvery { tDB.existe(tar) } returns true
        assertTrue(tDB.existe(tar))
    }

    @Test
    fun test_No_Tarea_Existe() = runBlocking {
        coEvery { tDB.existe(tar) } returns false
        assertFalse(tDB.existe(tar))
    }

    @Test
    fun test_Tarea_Guardar() {
        coEvery { tDB.guardar(tar) } returns true;
        assertTrue(tDB.guardar(tar))
    }

    @Test
    fun test_No_Tarea_Guardar() {
        coEvery { tDB.guardar(tar) } returns false;
        assertFalse(tDB.guardar(tar))
    }
}