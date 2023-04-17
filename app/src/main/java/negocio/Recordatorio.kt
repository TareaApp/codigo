package negocio

import integracion.RecordatorioDB
import integracion.TareaDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class Recordatorio {
    private lateinit var nombre: String
    private lateinit var asignatura: String
    private lateinit var descripcion: String
    private var fechaRecordatorio = Calendar.getInstance()

    companion object{
        private val rDBaux = RecordatorioDB()
        private lateinit var aux : ArrayList<Recordatorio>

        fun listarTodas(): ArrayList<Recordatorio>{

            return aux
        }
    }

    fun getNombre(): String{
        return nombre
    }

    fun getAsignatura(): String{
        return asignatura
    }

    fun getFechaRecordatorio(): Calendar{
        return fechaRecordatorio
    }



}