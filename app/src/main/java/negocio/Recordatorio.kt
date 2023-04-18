package negocio

import integracion.RecordatorioDB
import java.util.*

class Recordatorio {
    private lateinit var nombre: String
    private lateinit var categoria: String
    private lateinit var descripcion: String
    private lateinit var fecha: Calendar

    //Esto es por si lo necesita la vista, en lugar de tener sólo un Calendar
    private lateinit var anyo: Integer
    private lateinit var mes: Integer
    private lateinit var dia: Integer
    private lateinit var hora: Integer
    private lateinit var minutos: Integer

    private var recordDB = RecordatorioDB()

    private var fechaRecordatorio = Calendar.getInstance()
    constructor(nombre: String, categoria: String, descripcion: String = "", fecha: Calendar){ //Por completar

        this.nombre = nombre.trim()
        this.categoria = categoria.trim()
        this.descripcion = descripcion
        this.fecha = fecha
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