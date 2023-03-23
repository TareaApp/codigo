package integracion

import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.tasks.await
import negocio.Tarea
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

    private lateinit var t: Tarea
    constructor(){
    }

    fun guardar(t: Tarea): Boolean{

        try{
            val id = "${t.getNombre()}-${t.getAsignatura()}".uppercase().trim()
            SingletonDataBase.getInstance().getDB().collection(myCol).document(id).set(hashMapOf("Nombre" to t.getNombre()
                , "Asignatura" to t.getAsignatura(), "Descripcion" to t.getDescription(),
                "Duracion Horas" to t.getHora(), "Duracion Minutos" to t.getMinuto()))
        }
        catch (e: Exception){
            return false
        }
        return true
    }

     suspend fun existe(t: Tarea): Boolean{
        val doc = SingletonDataBase.getInstance().getDB().collection("Tareas").document("${t.getNombre()}-${t.getAsignatura()}".uppercase()).get().await()
        return doc.exists()
    }

    suspend fun listarTodas(): ArrayList<Tarea>{
            var lista = ArrayList<Tarea>()
            val querySnapshot= SingletonDataBase.getInstance().getDB().collection(myCol).orderBy(myPlanificacion).get().await()
            querySnapshot.forEach { doc ->
            val tarea = toTarea(doc)
            lista.add(tarea)
        }

            return lista
        }

    private fun toTarea(doc: QueryDocumentSnapshot):Tarea{
        var t = Tarea(doc.get(myNombre) as String, doc.get(myAsignatura) as String, (doc.get(myHora) as Long).toInt(),
            (doc.get(myMinuto) as Long).toInt(), doc.get(myDescripcion) as String, null)

        /*if(doc.get(myPlanificacion) != null){
            var cal = Calendar.getInstance()
            cal.setTime(doc.getDate(myPlanificacion))
            t.setPlan(cal)
        }*/
        return t
    }
    }


