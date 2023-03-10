package negocio

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class Tarea {
    private lateinit var nombre: String
    private lateinit var asignatura: String
    private lateinit var descripcion: String
    private var hora = 0
    private var minutos: Int = 0
    private val db = FirebaseFirestore.getInstance()

    constructor(nombre: String, asignatura: String, hora: Int, minutos: Int, descripcion: String = ""){
        this.nombre = nombre.trim()
        this.asignatura = asignatura.trim()
        this.hora = hora
        this.minutos = minutos
        this.descripcion = descripcion
    }
    constructor()

    suspend fun existe(): Boolean{
        val doc = db.collection("Tareas").document("$nombre-$asignatura".uppercase()).get().await()
        return doc.exists()
    }

    fun guardar(){
        val id = "$nombre-$asignatura".uppercase().trim()
        db.collection("Tareas").document(id).set(
            hashMapOf("Nombre" to nombre, "Asignatura" to asignatura, "Descripcion" to descripcion,
                "Duracion Horas" to hora, "Duracion Minutos" to minutos)
        )
    }

}