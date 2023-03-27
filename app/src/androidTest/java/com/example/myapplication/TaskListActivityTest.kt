package com.example.myapplication


import integracion.SingletonDataBase
import integracion.TareaDB
import kotlinx.coroutines.runBlocking
import negocio.Tarea
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class TaskListActivityTest {

    lateinit var tarAux : Tarea
    lateinit var fecha : Date
    private var numeroTareas : Int = 0
    private var nT : Int = 0
    private val tDB = TareaDB()

    @Before
    fun setUp(){
        fecha = Date(100)
        tarAux = Tarea("Nombre1", "Asignatura1", 11, 11, "Descripcion1")
    }

    @Test
    fun testNumberOfTasks() = runBlocking{
        //Cogemos el numero de tareas
        numeroTareas = tDB.listarTodas().size

        //Guardamos una nueva tarea
        tarAux.guardar()

        val id = "${tarAux.getNombre()}-${tarAux.getAsignatura()}".uppercase().trim()

        //Cogemos el numero de tareas +1
        nT = tDB.listarTodas().size

        //Eliminamos tarea de la base de datos
        SingletonDataBase.getInstance().getDB().collection("Tareas").document(id).delete()

        assertEquals(nT, numeroTareas)
    }


}