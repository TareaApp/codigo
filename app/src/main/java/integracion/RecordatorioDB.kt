package integracion

import kotlinx.coroutines.tasks.await
import negocio.Recordatorio
import negocio.Tarea

class RecordatorioDB {

    private val myCol = "Recordatorios"
    private val myNombre = "Nombre"
    private val myCategoria = "Categoria"
    private val myDescripcion = "Descripcion"
    private val myFecha = "Fecha"

    constructor(){
    }

    fun guardar(r: Recordatorio): Boolean {
        val id = "${r.getNombre()}-${r.getCategoria()}-${r.getFecha()}".uppercase().trim()
        try {
            SingletonDataBase.getInstance().getDB().collection(myCol).document(id).set(
                hashMapOf(
                    myNombre to r.getNombre(),
                    myCategoria to r.getCategoria(),
                    myFecha to r.getCategoria(),
                )
            )
        }
        catch (e: Exception){
            return false
        }
        return true
    }
    suspend fun existe(r: Recordatorio): Boolean {
        val id = "${r.getNombre()}-${r.getCategoria()}-${r.getFecha()}".uppercase().trim()
        val doc = SingletonDataBase.getInstance().getDB().collection(myCol).document(id).get().await()
        return doc.exists()
    }
}