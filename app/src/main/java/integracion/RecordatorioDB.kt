package integracion

import negocio.Recordatorio
import negocio.Tarea

class RecordatorioDB {

    private val myNombre = "Nombre"
    private val myCategoria = "Categoria"
    private val myDescripcion = "Descripcion"
    private val myFecha = "Descripcion"
    private val myHora = "Descripcion"

    constructor(){
    }

    fun guardar(r: Recordatorio): Boolean {
        //val id = "${r.getNombre()}-${r.getAsignatura()}".uppercase().trim()
        return true
    }
    suspend fun existe(t: Tarea): Boolean {
        return true
    }
}