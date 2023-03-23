package presentacion

import com.example.myapplication.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import negocio.Tarea
import java.util.ArrayList

class TareaArrayAdapter(context: Context, private val resource: Int, private val tareas: Array<Tarea>) :
    ArrayAdapter<Tarea>(context, resource, tareas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val tarea = tareas[position]

        val nombreTextView = view.findViewById<TextView>(R.id.tareaNombre)
        val asignaturaTextView = view.findViewById<TextView>(R.id.tareaAsignatura)

        nombreTextView.text = tarea.getNombre()
        asignaturaTextView.text = tarea.getAsignatura()

        return view
    }
}
