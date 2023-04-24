package com.example.myapplication

import integracion.SingletonDataBase
import kotlinx.coroutines.runBlocking
import negocio.Recordatorio
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*


class RecordatorioIntegrationTest {

    lateinit var recCorr : Recordatorio

    @Before
    fun setUp(){

        var c1 = Calendar.getInstance()
        c1.set(2023,4,10,12,3)
        c1.set(Calendar.SECOND, 0)
        c1.set(Calendar.MILLISECOND, 0)

        recCorr = Recordatorio("Correcto", "Correcto", "", c1)
    }

    @Test
    fun crearRecCorrecto() = runBlocking {
        var exito = recCorr.guardar()
        val timestamp = recCorr.getFecha().timeInMillis
        val id = "${recCorr.getNombre()}-${recCorr.getCategoria()}-${timestamp.toString()}".uppercase().trim()
        SingletonDataBase.getInstance().getDB().collection("Recordatorios").document(id).delete()
        Assert.assertTrue(exito)
    }

    @Test
    fun recNoExiste() = runBlocking {
        val timestamp = recCorr.getFecha().timeInMillis
        val id = "${recCorr.getNombre()}-${recCorr.getCategoria()}-${timestamp.toString()}".uppercase().trim()
        SingletonDataBase.getInstance().getDB().collection("Recordatorios").document(id).delete()
        var encontrado = recCorr.existe()
        Assert.assertFalse(encontrado)
    }

    @Test
    fun recExiste() = runBlocking {
        recCorr.guardar()
        val timestamp = recCorr.getFecha().timeInMillis
        var existe = recCorr.existe()
        val id = "${recCorr.getNombre()}-${recCorr.getCategoria()}-${timestamp.toString()}".uppercase().trim()
        SingletonDataBase.getInstance().getDB().collection("Recordatorios").document(id).delete()
        Assert.assertTrue(existe)
    }
}