package com.example.myapplication

import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import negocio.Tarea
import org.junit.Assert.*

import org.junit.Test

class TareaTest {

    @RelaxedMockK
    val tar = mockk<Tarea>()
        // prueba empezar pipeline

    @Test
    fun test_Tarea_Existe() = runBlocking {
        coEvery { tar.existe() } returns true
        assertTrue(tar.existe())
    }

    @Test
    fun test_No_Tarea_Existe() = runBlocking {
        coEvery { tar.existe() } returns false
        assertFalse(tar.existe())
    }

}