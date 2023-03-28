package com.example.myapplication

import integracion.SingletonDataBase
import integracion.TareaDB
import kotlinx.coroutines.runBlocking
import negocio.Tarea
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class MarcarCompletaIntegrationTest {

    lateinit var tarAux : Tarea
    lateinit var fecha : Date
    private var numeroTareas : Int = 0
    private var nT : Int = 0
    private val tDB = TareaDB()
    private var tmpFalse : Boolean = false
    private var tmpTrue : Boolean = false

    @Before
    fun setUp(){
        fecha = Date(100)
        tarAux = Tarea("Nombre123456", "Asignatura123456", 11, 11, "Descripcion1", fecha, false)
    }

    @Test
    fun testNumberOfTasks() = runBlocking{

        //Guardamos una nueva tarea
        tarAux.guardar()

        val id = "${tarAux.getNombre()}-${tarAux.getAsignatura()}".uppercase().trim()

        tmpFalse = tarAux.getCompletada()

        //Marcamos tarea como completada
        tarAux.completar(true)

        //Guardamos el cambio
        tmpTrue = tarAux.getCompletada()

        //Eliminamos tarea de la base de datos
        SingletonDataBase.getInstance().getDB().collection("Tareas").document(id).delete()

        //Si tmpFalse es False y tmpTrue es True, tiene que dar bien
        Assert.assertEquals(false, tmpFalse)
        Assert.assertEquals(true, tmpTrue)
    }



}