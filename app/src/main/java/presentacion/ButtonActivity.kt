package presentacion

import com.example.myapplication.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import negocio.Tarea


class ButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button)

        listaTar()
        val buttonToForm = findViewById<Button>(R.id.buttonToForm)

        buttonToForm.setOnClickListener {
            //startActivity(Intent(this, FormActivity::class.java))
            startActivity(Intent(this, TaskListActivity::class.java))
        }

    }

    fun listaTar(){
        val listView = findViewById<ListView>(R.id.listView)

        // Obtener array de tareas
        var taskList = Tarea.listarTodas()
        /*var taskList: Array<Tarea> = arrayOf(
            Tarea("Comprar pan", "Deberes", 2, 20, "Ir a comprar el pan",null),
            Tarea("Comprar pan", "Deberes", 2, 20, "Ir a comprar el pan",null),
            Tarea("Comprar pan", "Deberes", 2, 20, "Ir a comprar el pan",null),
            Tarea("Comprar pan", "Deberes", 2, 20, "Ir a comprar el pan",null),
            Tarea("Comprar pan", "Deberes", 2, 20, "Ir a comprar el pan",null),
            Tarea("Comprar pan", "Deberes", 2, 20, "Ir a comprar el pan",null),
            Tarea("Comprar pan", "Deberes", 2, 20, "Ir a comprar el pan",null),
            Tarea("Comprar pan", "Deberes", 2, 20, "Ir a comprar el pan",null),
            Tarea("Comprar pan", "Deberes", 2, 20, "Ir a comprar el pan",null),
            Tarea("Comprar pan", "Deberes", 2, 20, "Ir a comprar el pan",null),
            Tarea("Comprar pan", "Deberes", 2, 20, "Ir a comprar el pan",null),
            Tarea("Comprar pan", "Deberes", 2, 20, "Ir a comprar el pan",null),
            Tarea("Comprar pan", "Deberes", 2, 20, "Ir a comprar el pan",null),
            Tarea("Comprar pan", "Deberes", 2, 20, "Ir a comprar el pan",null),
            Tarea("Comprar pan", "Deberes", 2, 20, "Ir a comprar el pan",null),
            Tarea("Comprar pan", "Deberes", 2, 20, "Ir a comprar el pan",null),
        )*/
        

        // Añade tus tareas al ArrayList tareas aquí

        val adapter = TareaArrayAdapter(this, R.layout.tarea_item, taskList)
        listView.adapter = adapter
    }
    }
