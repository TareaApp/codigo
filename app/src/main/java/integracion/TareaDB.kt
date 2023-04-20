package integracion

import android.widget.Toast
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.tasks.await
import negocio.Tarea
import java.util.Calendar
import java.util.*
import kotlin.collections.ArrayList

class TareaDB {

    private val myCol = "Tareas"
    private val myNombre = "Nombre"
    private val myAsignatura = "Asignatura"
    private val myDescripcion = "Descripcion"
    private val myHora = "Duracion Horas"
    private val myMinuto = "Duracion Minutos"
    private val myPlanificacion = "Planificacion"
    private val myCompletada = "Completada"


    constructor(){
    }

    /**
     * Esta funcion solo guardará tareas que tengan una planificación
     * @param t Tarea
     * @author Carlos Gomes
     * @return Delvuelve True si la tarea se ha podido en la base de datos, False en caso contrario
     * @exception Esta funcion captura cualquier excepcion lanzada por la base de datos, en caso de que ocurra devolverá false
     */
    fun guardar(t: Tarea): Boolean{

        try{
            val id = "${t.getNombre()}-${t.getAsignatura()}".uppercase().trim()
            if (t.getPlan() != null) {
                SingletonDataBase.getInstance().getDB().collection(myCol).document(id).set(
                    hashMapOf(
                        myNombre to t.getNombre(),
                        myAsignatura to t.getAsignatura(),
                        myDescripcion to t.getDescription(),
                        myHora to t.getHora(),
                        myMinuto to t.getMinuto(),
                        myPlanificacion to t.getPlan()!!.time,
                        myCompletada to t.getCompletada()
                    )
                )
            }
            else{
                return false
            }
        }
        catch (e: Exception){
            return false
        }
        return true
    }

    /**
     * @param t Tarea
     * @author Carlos Gomes
     * @return Delvuelve True si la tarea existe en la base de datos, False en caso contrario
     */
    suspend fun existe(t: Tarea): Boolean{
        val doc = SingletonDataBase.getInstance().getDB().collection(myCol).document("${t.getNombre()}-${t.getAsignatura()}".uppercase()).get().await()
        return doc.exists()
    }
    //
    /**
     * Para llamar a esta funcion hay que asegurarse que tiene planificacion la tarea de lo contrario no va
     * @param t Tarea
     * @author Carlos Gomes
     * @return Devuelve una lista de tareas cuyas planificaciones van desde 2 dias antes de la planificicacion de la tarea pasada por parametro en adelante
     */
    suspend fun tareasPosteriores(t: Tarea): MutableList<Tarea>{
        var tareas = mutableListOf<Tarea>()
        t.getPlan()!!.add(Calendar.DATE, -2)

        val querySnapshot= SingletonDataBase.getInstance().getDB().collection(myCol).whereNotEqualTo(myPlanificacion, false).whereGreaterThan(myPlanificacion, (t.getPlan()!!.time)).orderBy(myPlanificacion).get().await()
        querySnapshot.forEach { doc ->
            val tarea = toTarea(doc)
            tareas.add(tarea)
        }
        t.getPlan()!!.add(Calendar.DATE, 2)
        return tareas
    }

    fun completar(t: Tarea, completado: Boolean){
        val tareaRef = SingletonDataBase.getInstance().getDB().collection(myCol).document("${t.getNombre()}-${t.getAsignatura()}".uppercase())
        tareaRef.update(myCompletada, completado).addOnSuccessListener {
            println("se ha completado con exito")
        }.addOnFailureListener { e ->
            println("no se ha completado")
        }
    }

    suspend fun agregarAtributo(){
        var lista = ArrayList<Tarea>()
        val querySnapshot= SingletonDataBase.getInstance().getDB().collection(myCol).get().await()
        querySnapshot.forEach { doc ->
            doc.reference.update(myCompletada, false)
        }
    }

    suspend fun listarTodas(): ArrayList<Tarea>{

        var lista = ArrayList<Tarea>()
        val querySnapshot= SingletonDataBase.getInstance().getDB().collection(myCol).orderBy(myPlanificacion).get().await()
        //val querySnapshot= SingletonDataBase.getInstance().getDB().collection(myCol).get().await()
        querySnapshot.forEach { doc ->
            val tarea = toTarea(doc)
            lista.add(tarea)
        }

            return lista
    }

    private fun toTarea(doc: QueryDocumentSnapshot):Tarea{
        var t = Tarea(doc.get(myNombre) as String, doc.get(myAsignatura) as String, (doc.get(myHora) as Long).toInt(),
            (doc.get(myMinuto) as Long).toInt(), doc.get(myDescripcion) as String, doc.get(myCompletada) as Boolean
        )

        if(doc.get(myPlanificacion) != null){
            var cal = Calendar.getInstance()
            cal.setTime(doc.getDate(myPlanificacion))
            t.setPlan(cal)
        }
        return t
    }


}

