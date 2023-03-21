package negocio

import com.google.firebase.firestore.FirebaseFirestore
import integracion.TareaDB
import kotlinx.coroutines.tasks.await
import java.util.*

class Tarea {
    private lateinit var nombre: String
    private lateinit var asignatura: String
    private lateinit var descripcion: String
    private var hora = 0
    private var minutos: Int = 0

    private lateinit var fechaPlan : Calendar

    private val tDB = TareaDB()

    constructor(nombre: String, asignatura: String, hora: Int, minutos: Int, descripcion: String = ""){
        this.nombre = nombre.trim()
        this.asignatura = asignatura.trim()
        this.hora = hora
        this.minutos = minutos
        this.descripcion = descripcion


    }

    constructor(nombre: String, asignatura: String, hora: Int, minutos: Int, descripcion: String = "",fechaPlan : Calendar){
        this.nombre = nombre.trim()
        this.asignatura = asignatura.trim()
        this.hora = hora
        this.minutos = minutos
        this.descripcion = descripcion
        this.fechaPlan = fechaPlan

    }
    constructor()

    suspend fun existe(): Boolean{
        return tDB.existe(this)
    }

    fun guardar(): Boolean{
        return tDB.guardar(this)
    }

    fun listarTodas(): Array<Tarea>{
        return tDB.listarTodas()
    }

    fun getNombre(): String{
        return nombre
    }
    fun getDescription(): String{
        return descripcion
    }
    fun getAsignatura(): String{
        return asignatura
    }

    fun getHora(): Int{
        return hora
    }
    fun getMinuto(): Int{
        return minutos
    }

    fun planificar() : Boolean{

        return true
    }


}