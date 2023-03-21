package integracion

import kotlinx.coroutines.tasks.await
import negocio.Tarea
import java.util.*
import kotlin.collections.ArrayList

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

    fun listarTodas(): Array<Tarea>{
            var lista = ArrayList<Tarea>()
            val col = SingletonDataBase.getInstance().getDB().collection(myCol)
        //TODO no funciona, supongo que no es la funcion que toca
            col.get().addOnSuccessListener { documentos ->
                for (documento in documentos) {
                    // procesar cada documento
                    val nombre = documento.data["Nombre"]
                    val asignatura = documento.data["Asignatura"]
                    val descripc = documento.data["Descripcion"]
                    val dHoras = documento.data["Duracion Horas"]
                    val dMinutos = documento.data["Duracion Minutos"]
                    lista.add(Tarea(nombre as String,
                        asignatura as String, dHoras as Int, dMinutos as Int, descripc as String,null ))
                }
            }.addOnFailureListener { excepcion ->
                // manejar errores
            }

            return lista as Array<Tarea>
        }

    }


