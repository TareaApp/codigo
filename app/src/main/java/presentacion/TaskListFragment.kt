package presentacion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.Button
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
        var resultArray = arrayOf<String>()
        for (element in taskList) {
            val joinedString = element!!.getNombre() + " - " + element.getAsignatura() //Concatenamos ambos strings con un espacio en blanco
            resultArray += joinedString //Añadimos el string concatenado al array resultante
        }
        // Crear un ArrayAdapter para mostrar la lista de tareas
        var adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_2,
            android.R.id.text1,
            resultArray
        )

        if (listView != null) {
            listView.adapter = adapter
        }

        val buttonToForm = view.findViewById<Button>(R.id.buttonToForm)
        if (buttonToForm != null) {
            buttonToForm.setOnClickListener {
                startActivity(Intent(requireContext(), FormActivity::class.java))
            }
        }
    }
}


