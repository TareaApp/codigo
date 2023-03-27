package com.example.myapplication

import integracion.SingletonDataBase
import kotlinx.coroutines.runBlocking
import negocio.Tarea
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class TareaIntegrationTest {
    lateinit var tarCorr : Tarea
    lateinit var tarNoExiste : Tarea
    lateinit var tarPlaniBien : Tarea
    lateinit var fecha : Calendar

    @Before
    fun setUp(){
        fecha = Calendar.getInstance()
        fecha.set(Calendar.HOUR_OF_DAY,2)
        tarCorr = Tarea("Nombre", "Asignatura", 12, 12, "Descripcion", fecha)
        tarNoExiste = Tarea("No existo", "nada", 12, 12, "Descripcion", fecha)
        tarPlaniBien = Tarea("No existo", "nada", 12, 12, "Descripcion", fecha)

    }

    @Test
    fun crearTareaCorrecto() = runBlocking {
        var exito = tarCorr.guardar()
        val id = "${tarCorr.getNombre()}-${tarCorr.getAsignatura()}".uppercase().trim()
        SingletonDataBase.getInstance().getDB().collection("Tareas").document(id).delete()
        assertTrue(exito)
    }
    @Test
    fun crearTareaInexistente() = runBlocking {
        var encontrado = tarNoExiste.existe()
        assertEquals(false,encontrado)
    }

    @Test
    fun planificaBien() = runBlocking {
        var planificaBien = tarPlaniBien.planificar();
        assertTrue(planificaBien)
    }

}