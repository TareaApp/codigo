package presentacion


import android.content.Context

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

import java.util.ArrayList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import negocio.Tarea

class TareaArrayAdapter(context: Context, private val resource: Int, private val tareas: ArrayList<Tarea>) :
    ArrayAdapter<Tarea>(context, resource, tareas), AdapterView.OnItemClickListener {

    private val fragmentManager = (context as FragmentActivity).supportFragmentManager

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val tarea = tareas[position]

        val nombreTextView = view.findViewById<TextView>(R.id.tareaNombre)
        val asignaturaTextView = view.findViewById<TextView>(R.id.tareaAsignatura)

        nombreTextView.text = tarea.getNombre()
        asignaturaTextView.text = tarea.getAsignatura()

        view.setOnClickListener {
            onItemClick(parent as AdapterView<*>, view, position, getItemId(position))
        }

        return view
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val tarea = getItem(position)
        val fragment = DetalleTareaFragment.newInstance(tarea)
        fragment.setTarea(tarea)
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentTareas, fragment)
            .addToBackStack(null)
            .commit()
    }
}

