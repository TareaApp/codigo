package presentacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.ArrayAdapter
import com.example.myapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import negocio.Tarea

class TaskListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tareas)

        // Obtener una referencia a la ListView
        val listView = findViewById<ListView>(R.id.listView)
        // Obtener array de tareas

        val taskList = Tarea.listarTodas()
        var resultArray = arrayOf<String>()
        for (element in taskList) {
            val joinedString = element!!.getNombre() + " - " + element.getAsignatura() //Concatenamos ambos strings con un espacio en blanco
            resultArray += joinedString //Añadimos el string concatenado al array resultante
        }
        // Crear un ArrayAdapter para mostrar la lista de tareas
        var adapter = ArrayAdapter(
            getContext(),
            android.R.layout.simple_list_item_2,
            android.R.id.text1,
            resultArray
        )
        // Asignar el ArrayAdapter a la ListView
        listView.adapter = adapter

    }


    private fun getContext() : AppCompatActivity{
        return this
    }
}


