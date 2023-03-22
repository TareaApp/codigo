package integracion

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.tasks.await
import negocio.Tarea
import java.util.Calendar

class TareaDB {

    private val myCol = "Tareas"
    private val myNombre = "Nombre"
    private val myAsignatura = "Asignatura"
    private val myDescripcion = "Descripcion"
    private val myHora = "Duracion Horas"
    private val myMinuto = "Duracion Minutos"
    private val myPlanificacion = "Planificacion"


    private lateinit var t: Tarea
    constructor(){
    }

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
                        myPlanificacion to t.getPlan()!!.time
                    )
                )
            }
        }
        catch (e: Exception){
            return false
        }
        return true
    }

    suspend fun existe(t: Tarea): Boolean{
        val doc = SingletonDataBase.getInstance().getDB().collection(myCol).document("${t.getNombre()}-${t.getAsignatura()}".uppercase()).get().await()
        if(doc.get(myPlanificacion) != null){
            var cal = Calendar.getInstance()
            cal.setTime(doc.getDate(myPlanificacion))
            t.setPlan(cal)
        }
        return doc.exists()
    }
    //Para llamar a esta funcion hay que asegurarse que tiene planificacion la tarea de lo contrario no va
    suspend fun tareasPosteriores(t: Tarea): MutableList<Tarea>{
        var tareas = mutableListOf<Tarea>()
        t.getPlan()!!.add(Calendar.DATE, -2)

        val querySnapshot= SingletonDataBase.getInstance().getDB().collection(myCol).whereNotEqualTo(myPlanificacion, false).whereGreaterThan(myPlanificacion, (t.getPlan()!!.time)).orderBy(myPlanificacion).get().await()
        querySnapshot.forEach { doc ->
            val tarea = toTarea(doc)
            tareas.add(tarea)
        }
        t.getPlan()!!.add(Calendar.DATE, 2)
        println(tareas)
        return tareas
    }

    private fun toTarea(doc: QueryDocumentSnapshot):Tarea{
        var t = Tarea(doc.get(myNombre) as String, doc.get(myAsignatura) as String, (doc.get(myHora) as Long).toInt(),
            (doc.get(myMinuto) as Long).toInt(), doc.get(myDescripcion) as String)

        if(doc.get(myPlanificacion) != null){
            var cal = Calendar.getInstance()
            cal.setTime(doc.getDate(myPlanificacion))
            t.setPlan(cal)
        }
        return t
    }


}