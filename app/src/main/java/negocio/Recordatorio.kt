package negocio

import integracion.RecordatorioDB
import integracion.TareaDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class Recordatorio {
    private lateinit var nombre: String
    private lateinit var categoria: String
    private lateinit var descripcion: String
    private lateinit var fecha: Calendar

    private var recordDB = RecordatorioDB()
    constructor(nombre: String, categoria: String, descripcion: String = "", fecha: Calendar){

        this.nombre = nombre.trim()
        this.categoria = categoria.trim()
        this.descripcion = descripcion
        this.fecha = fecha
    }
    companion object {
        private val rDBaux = RecordatorioDB()
        private lateinit var aux: ArrayList<Recordatorio>

        fun listarTodas(): ArrayList<Recordatorio> {
            val launch = CoroutineScope(Dispatchers.IO).launch {
                aux = rDBaux.listarTodas()
                return@launch
            }
            while (!launch.isCompleted) {
            }

            return aux
        }
    }
    fun guardar(): Boolean {
        return recordDB.guardar(this);
    }

    suspend fun existe(): Boolean{
        return recordDB.existe(this);
    }

    fun getDescripcion(): String{
        return descripcion
    }

    fun getNombre(): String{
        return nombre
    }

    fun getCategoria() : String{
        return categoria
    }

    fun getFecha(): Calendar{
        return fecha
    }


}