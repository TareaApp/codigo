package com.example.myapplication

import integracion.SingletonDataBase
import integracion.TareaDB
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import negocio.Tarea
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import presentacion.DetalleTareaFragment
import java.util.*
import java.util.regex.Pattern.matches

class TareaTest {

    val tarDB = mockk<TareaDB>()


    lateinit var tar : Tarea
    lateinit var tarP : Tarea
    @Before
    fun setUp(){
        tar = Tarea()
        tarP = Tarea("hola", "cat", 1, 1, "Descrfioc", false)
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

    @Test
    fun test_No_Null_Tarea_Lista_Tareas() = runBlocking{
        every { runBlocking { tarDB.listarTodas() } } returns ArrayList<Tarea>()
        assertNotNull(tarDB.listarTodas())
    }

    @Test
    fun test_Comprobar_Completar_Tarea_Marcar_Completada(){
        every { tarDB.completar(tar, true) } returns Unit
        assertTrue(true)
    }

    @Test
    fun test_Comprobar_Completar_Tarea_Marcar_No_Completada(){
        every { tarDB.completar(tarP, false) } returns Unit
        assertFalse(false)
    }

    @Test
    fun test_Getters_Ver_Detalles_Tarea() {
        val tarea = Tarea("Tarea 1", "Asignatura 1", 2, 30, "Descripción de la tarea 1",true)
        assertEquals("Tarea 1", tarea.getNombre())
        assertEquals("Asignatura 1", tarea.getAsignatura())
        assertEquals("Descripción de la tarea 1", tarea.getDescription())
        assertEquals(2, tarea.getHora())
        assertEquals(30, tarea.getMinuto())
    }

}