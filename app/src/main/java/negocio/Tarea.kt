package negocio

import com.google.firebase.firestore.FirebaseFirestore
import integracion.TareaDB
import kotlinx.coroutines.tasks.await

class Tarea {
    private lateinit var nombre: String
    private lateinit var asignatura: String
    private lateinit var descripcion: String
    private var hora = 0
    private var minutos: Int = 0

    private var diaPlanificacion : Int =  0

    private  var mesPlanificacion :Int =0
    private  var horaPlanificacion :Int=0

    private  var minPlanificacion :Int=0

    private val tDB = TareaDB()

    constructor(nombre: String, asignatura: String, hora: Int, minutos: Int, descripcion: String = "",mesPlanificacion:Int ,diaPlanificacion :Int, horaPlanificacion :Int ,minPlanificacion :Int){
        this.nombre = nombre.trim()
        this.asignatura = asignatura.trim()
        this.hora = hora
        this.minutos = minutos
        this.descripcion = descripcion

        this.diaPlanificacion = diaPlanificacion
        this.horaPlanificacion=horaPlanificacion
        this.mesPlanificacion = mesPlanificacion
        this.minPlanificacion=minPlanificacion
    }
    constructor()

    suspend fun existe(): Boolean{
        return tDB.existe(this)
    }

    fun guardar(): Boolean{
        return tDB.guardar(this)
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


}