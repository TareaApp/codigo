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
import android.widget.Toast
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

        if(!taskList.isEmpty()){
            val adapter = TareaArrayAdapter(this, R.layout.tarea_item, taskList)
            listView.adapter = adapter
        }
        else{
            Toast.makeText(applicationContext, "No hay tareas", Toast.LENGTH_LONG).show()
        }
        

        // Añade tus tareas al ArrayList tareas aquí


    }
    }
