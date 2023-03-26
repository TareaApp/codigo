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

    private var fechaPlan : Calendar? = null
    private var tDB = TareaDB()

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

    //Necesito este setDB para las pruebas Unitarias, no borrar
    fun setDB(db : TareaDB){
        this.tDB = db
    }
    fun guardar(): Boolean{
        return tDB.guardar(this)
    }

     fun listarTodas(): MutableList<Tarea>{
        return mutableListOf()
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

    fun setHora(hora : Int){
        this.hora=hora
    }
    fun setMinuto(min: Int){
        this.minutos=min
    }
    fun getPlan(): Calendar?{
        return fechaPlan
    }
    fun setPlan(cal: Calendar){
        fechaPlan = cal
    }

    suspend fun setPlanNull() { //Le da como planificación la primera disponible

        // cojo mi tarea, la pongo al final de la tarea que estoy mirando, miro donde acabaria y si chocaria con la siguiente

        var hoy = Calendar.getInstance()
        var lista = tDB.tareasPosteriores(this)

        var posible = true
        var encontrado= false
        for (i in 0 until lista.size - 1) {
            if (hoy.compareTo(lista[i].getPlan()!!) == -1) { //SOLO ES VALIDA SI ES EN FECHAS SUPERARIORES A LA ACTUALIDAD
                //el principio y final de la tarea que estoy mirando
                var inicioPrimera = lista[i].getPlan()!!.clone() as Calendar
                var finPrimera = lista[i].getPlan()!!.clone() as Calendar
                finPrimera!!.add(Calendar.HOUR_OF_DAY, lista[i].getHora())
                finPrimera!!.add(Calendar.MINUTE, lista[i].getMinuto())

                //le pongo a mi tarea la fecha de donde acaba la que estoy mirando  y le sumo su duracion para ver donde acaba
                val finThis = finPrimera.clone() as Calendar
                finThis!!.add(Calendar.HOUR_OF_DAY, this.getHora())
                finThis!!.add(Calendar.MINUTE, this.getMinuto())

                //el principio y final de la siguiente tarea
                var inicioSegunda = lista[i + 1].getPlan()!!.clone() as Calendar
                var finSegunda = lista[i + 1].getPlan()!!.clone() as Calendar
                finSegunda!!.add(Calendar.HOUR_OF_DAY, lista[i + 1].getHora())
                finSegunda!!.add(Calendar.MINUTE, lista[i + 1].getMinuto())

               //si la tarea que quiero meter termina despues de que empiece la siguiente, Sé seguro que empieza antes (Por la logica de la app)
                posible = finThis.compareTo(inicioSegunda) == -1

                if (posible) { //si mi tarea cabe entre ambas tareas, le doy la fecha de cuando acaba la primera
                    var fec = finPrimera.clone() as Calendar
                    this.setPlan(fec)
                    encontrado=true
                    break;
                }
            }
        }

        if(!encontrado){   //si no he encontrado hueco me pongo al final del ultimo elemento
            var fec = lista[lista.size-1].getPlan()!!.clone() as Calendar
            this.setPlan(fec)

        }
    }

    suspend fun planificar() : Boolean {  //Funciona Limpia codigo

        var lista = tDB.tareasPosteriores(this)

        val finThis = this.getPlan()!!.clone() as Calendar
        finThis!!.add(Calendar.HOUR_OF_DAY, this.getHora())
        finThis!!.add(Calendar.MINUTE, this.getMinuto())

        for (tarea in lista) {

            val finTarea = tarea.getPlan()!!.clone() as Calendar
            finTarea!!.add(Calendar.HOUR_OF_DAY, tarea.getHora())
            finTarea!!.add(Calendar.MINUTE, tarea.getMinuto())

            //Esto esta asi para que se pueda diferencias bien cada caso y sea mas legible
            if (tarea.getPlan()!!.compareTo(this.getPlan()!!) == 1 && tarea.getPlan()!!.compareTo(finThis!!) == -1) {
                return false
            } else if (finTarea.compareTo(this.getPlan()!!) == 1 && finTarea.compareTo(finThis!!) == -1) {
                return false
            }else if(tarea.getPlan()!!.compareTo(this.getPlan()!!) == -1 && finTarea.compareTo(this.getPlan()!!) == 1){
                return false
            }
        }
        return true
    }
    suspend fun listarTareasParaPlanificiar(): MutableList<Tarea>{
        return tDB.tareasPosteriores(this)
    }

}