package integracion

import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.tasks.await
import negocio.Recordatorio
import negocio.Tarea
import java.util.*
import kotlin.collections.ArrayList

class RecordatorioDB {

    private val myCol = "Recordatorios"
    private val myNombre = "Nombre"
    private val myCategoria = "Categoria"
    private val myDescripcion = "Descripcion"
    private val myFecha = "Fecha"
    constructor(){
    }

    fun guardar(r: Recordatorio): Boolean {
        val id = "${r.getNombre()}-${r.getCategoria()}-${r.getFecha().time}".uppercase().trim()
        try {
            SingletonDataBase.getInstance().getDB().collection(myCol).document(id).set(
                hashMapOf(
                    myNombre to r.getNombre(),
                    myCategoria to r.getCategoria(),
                    myFecha to r.getFecha().time,
                    myDescripcion to r.getDescripcion()
                )
            )
        }
        catch (e: Exception){
            return false
        }
        return true
    }
    suspend fun existe(r: Recordatorio): Boolean {
        val id = "${r.getNombre()}-${r.getCategoria()}-${r.getFecha().time}".uppercase().trim()
        val doc = SingletonDataBase.getInstance().getDB().collection(myCol).document(id).get().await()
        return doc.exists()
    }
    suspend fun listarTodas(): ArrayList<Recordatorio> {
        var lista = ArrayList<Recordatorio>()
        val querySnapshot= SingletonDataBase.getInstance().getDB().collection(myCol).orderBy(myFecha).get().await()
        querySnapshot.forEach { doc ->
            val rec = toRecordatorio(doc)
            lista.add(rec)
        }

        return lista
    }

    private fun toRecordatorio(doc: QueryDocumentSnapshot):Recordatorio{
        var r = Recordatorio(doc.get(myNombre) as String, doc.get(myCategoria) as String, doc.get(myDescripcion) as String,
            doc.get(myFecha) as Calendar)
            //DUDA, cómo manejo esto

        return r
    }
}