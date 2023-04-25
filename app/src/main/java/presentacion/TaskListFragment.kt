package presentacion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import negocio.Tarea

class TaskListFragment: Fragment()  {

    lateinit var vista: View
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
        this.vista = view

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
    override fun onResume() {
        super.onResume()

        val listView = vista.findViewById<ListView>(R.id.listView)

        val taskList = Tarea.listarTodas()


        // Obtener array de tareas

        if(!taskList.isEmpty()){
            val adapter = TareaArrayAdapter(vista.context, R.layout.tarea_item, taskList)
            listView.adapter = adapter
        }
        else{
            Toast.makeText(vista.context, "No hay tareas", Toast.LENGTH_LONG).show()
        }

        val buttonToForm = vista.findViewById<Button>(R.id.buttonToForm)
        if (buttonToForm != null) {
            buttonToForm.setOnClickListener {
                startActivity(Intent(requireContext(), FormActivity::class.java))
            }
        }
    }
}


