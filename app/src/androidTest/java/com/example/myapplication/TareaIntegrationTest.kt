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
        var c1 = Calendar.getInstance()
        c1.set(2023,4,10,12,25)
        var c2 = Calendar.getInstance()
        c2.set(2023,4,10,14,0)
        var c3 = Calendar.getInstance()
        c3.set(2025,4,11,17,30)
        tarCorr = Tarea("Nombre", "Asignatura", 12, 12, "Descripcion" )
        tarNoExiste = Tarea("No existo", "nada", 12, 12, "Descripcion" )
        tarPlaniBien = Tarea("No existo", "nada", 12, 12, "Descripcion")
        tarCorr.setPlan(c1)
        tarNoExiste.setPlan(c2)
        tarPlaniBien.setPlan(c3)
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
        val id = "${tarPlaniBien.getNombre()}-${tarPlaniBien.getAsignatura()}".uppercase().trim()
        SingletonDataBase.getInstance().getDB().collection("Tareas").document(id).delete()
        assertTrue(planificaBien)
    }

}