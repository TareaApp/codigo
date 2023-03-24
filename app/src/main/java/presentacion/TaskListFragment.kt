package presentacion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import negocio.Tarea

class TaskListFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tareas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView = view.findViewById<ListView>(R.id.listView)

        val taskList = Tarea.listarTodas()


        // Obtener array de tareas

        if(!taskList.isEmpty()){
            val adapter = TareaArrayAdapter(view.context, R.layout.tarea_item, taskList)
            listView.adapter = adapter
        }
        else{
            Toast.makeText(view.context, "No hay tareas", Toast.LENGTH_LONG).show()
        }

        val buttonToForm = view.findViewById<Button>(R.id.buttonToForm)
        if (buttonToForm != null) {
            buttonToForm.setOnClickListener {
                startActivity(Intent(requireContext(), FormActivity::class.java))
            }
        }
    }
}


