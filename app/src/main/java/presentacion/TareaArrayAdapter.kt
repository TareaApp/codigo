package presentacion


import android.content.Context

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import negocio.Tarea
import java.text.DateFormatSymbols
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import negocio.Tarea
import java.util.*

class TareaArrayAdapter(context: Context, private val resource: Int, private val tareas: ArrayList<Tarea>) :
    ArrayAdapter<Tarea>(context, resource, tareas), AdapterView.OnItemClickListener {

    private val fragmentManager = (context as FragmentActivity).supportFragmentManager

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val tarea = tareas[position]

        val nombreTextView = view.findViewById<TextView>(R.id.tareaNombre)
        val planificacionTextView = view.findViewById<TextView>(R.id.tareaPlanificacion)
        val diaSemana = tarea.getPlan()!!.get(Calendar.DAY_OF_WEEK).toString()
        val dia = tarea.getPlan()!!.get(Calendar.DAY_OF_MONTH).toString()
        val mes = tarea.getPlan()!!.get(Calendar.MONTH)
        val year = tarea.getPlan()!!.get(Calendar.YEAR).toString()
        val symbols = DateFormatSymbols(Locale("es", "ES"))
        val nombreMes = symbols.months[mes]

        nombreTextView.text = tarea.getNombre()
        planificacionTextView.text = dia + " de " + nombreMes + " de " + year

        view.setOnClickListener {
            onItemClick(parent as AdapterView<*>, view, position, getItemId(position))
        val asignaturaTextView = view.findViewById<TextView>(R.id.tareaAsignatura)
        val checkBox = view.findViewById<CheckBox>(R.id.checkbox)

        nombreTextView.text = tarea.getNombre()
        asignaturaTextView.text = tarea.getAsignatura()
        checkBox.isChecked = tarea.getCompletada()

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            tarea.completar(isChecked)
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

