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
    constructor(nombre: String, categoria: String, descripcion: String = "", anyo: Integer, mes: Integer,
                dia: Integer, hora: Integer, minutos: Integer){ //Por completar

        this.nombre = nombre.trim()
        this.categoria = categoria.trim()
        this.descripcion = descripcion

        this.anyo = anyo
        this.mes = mes
        this.dia = dia
        this.hora = hora
        this.minutos = minutos
    }
    fun guardar(): Boolean {
        return recordDB.guardar(this);
    }

    suspend fun existe(): Boolean{
        return recordDB.existe(this);
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