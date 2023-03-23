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

    suspend fun setPlanNull(){ //Le da como planificación la primera disponible

        this.setPlan(Calendar.getInstance()) // le damos una planificación
        var lista = tDB.tareasPosteriores(this)

        for (i in 0 until lista.size-1) {
            var primera = lista[i].getPlan()!!
            var segunda = lista[i+1].getPlan()!!

            if(primera.get(Calendar.HOUR)+lista[i].getHora() + (primera.get(Calendar.MINUTE)+lista[i].getMinuto())/60>24) {
                //si la tarea abarca dos dias, movemos el dia, hora y duración
                if(primera.get(Calendar.DAY_OF_MONTH) +1 >29 && primera.get(Calendar.MONTH) == 1 || primera.get(Calendar.DAY_OF_MONTH) +1 == 31 && (primera.get(Calendar.MONTH) == 3 || primera.get(Calendar.MONTH) == 5 ||primera.get(Calendar.MONTH) == 8 &&primera.get(Calendar.MONTH) == 10 )) { //135810
                    if(primera.get(Calendar.MONTH)+1 ==13){
                        primera.set(Calendar.MONTH,1)
                        primera.set(Calendar.DAY_OF_MONTH,1)
                        primera.set(Calendar.YEAR,primera.get(Calendar.YEAR)+1)
                    }else{
                        primera.set(Calendar.MONTH,primera.get(Calendar.MONTH)+1)
                    }
                }else{
                    primera.set(Calendar.DAY_OF_MONTH,primera.get(Calendar.DAY_OF_MONTH)+1)
                }
                primera.set(Calendar.HOUR, (primera.get(Calendar.HOUR)+lista[i].getHora() + (primera.get(Calendar.MINUTE)+lista[i].getMinuto())/60)-24)
                lista[i].setHora(0)
                lista[i].setMinuto(0)
            }
               if(primera.get(Calendar.DAY_OF_MONTH)==segunda.get(Calendar.DAY_OF_MONTH) &&  primera.get(Calendar.MONTH)==segunda.get(Calendar.MONTH)){
                   if(lista[i].getHora()*60+ lista[i].getMinuto()+ this.getHora()*60+this.getMinuto() < (segunda.get(Calendar.HOUR) -primera.get(Calendar.HOUR))*60 + segunda.get(Calendar.MINUTE) -primera.get(Calendar.MINUTE)){
                       var fecha = primera
                       fecha.set(primera.get(Calendar.HOUR)+lista[i].getHora(), primera.get(Calendar.MINUTE)+lista[i].getMinuto())
                       this.setPlan(fecha)
                       break
                   }
               }else{
                   if(24 - (lista[i].getHora()+ lista[i].getMinuto()/60+ this.getHora()+this.getMinuto()/60) + segunda.get(Calendar.HOUR) + primera.get(Calendar.MINUTE)/60  < this.getHora()+this.getMinuto()){
                       var fecha = primera
                       fecha.set(primera.get(Calendar.HOUR)+lista[i].getHora(), primera.get(Calendar.MINUTE)+lista[i].getMinuto())
                       this.setPlan(fecha)
                       break
                   }
               }
            }
    }

    suspend fun planificar() : Boolean{

        var lista = tDB.tareasPosteriores(this)
        for (i in 0 until lista.size-1) {
            var primera = lista[i].getPlan()
            var segunda = lista[i + 1].getPlan()
            if (primera!!.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance()
                    .get(Calendar.DAY_OF_MONTH) && primera!!.get(Calendar.MONTH) == Calendar.getInstance()
                    .get(Calendar.MONTH)) {
                if (lista[i].getHora() * 60 + lista[i].getMinuto() + this.getHora() * 60 + this.getMinuto() >
                    (segunda!!.get(Calendar.HOUR) - primera!!.get(Calendar.HOUR)) * 60 + segunda!!.get(Calendar.MINUTE) - primera!!.get(Calendar.MINUTE)) {
                    return false
                }
            }
        }
        return true
    }

    suspend fun listarTareasParaPlanificiar(): MutableList<Tarea>{
        return tDB.tareasPosteriores(this)
    }

}