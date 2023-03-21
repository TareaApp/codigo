package presentacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.*
import com.example.myapplication.R
import negocio.Tarea

class TaskListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tareas)

        // Obtener una referencia a la ListView
        val listView = findViewById<ListView>(R.id.listView)

        // Obtener array de tareas
        var taskList = Tarea.listarTodas()

        // Crear un ArrayAdapter para mostrar la lista de tareas
        val adapter = ArrayAdapter<Tarea>(
            this,
            android.R.layout.simple_list_item_2,
            android.R.id.text1,
            taskList
        )

        // Asignar el ArrayAdapter a la ListView
        listView.adapter = adapter
    }
}
