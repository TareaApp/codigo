package integracion

import kotlinx.coroutines.tasks.await
import negocio.Tarea

class TareaDB {

    private val myCol = "Tareas"
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


}