package presentacion

import com.example.myapplication.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import negocio.Tarea
import java.text.DateFormatSymbols
import java.util.*

class TareaArrayAdapter(context: Context, private val resource: Int, private val tareas: ArrayList<Tarea>) :
    ArrayAdapter<Tarea>(context, resource, tareas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val tarea = tareas[position]

        val nombreTextView = view.findViewById<TextView>(R.id.tareaNombre)
        val planificacionTextView = view.findViewById<TextView>(R.id.tareaPlanificacion)
        val dia = tarea.getPlan()!!.get(Calendar.DAY_OF_MONTH).toString()
        val mes = tarea.getPlan()!!.get(Calendar.MONTH)
        val year = tarea.getPlan()!!.get(Calendar.YEAR).toString()
        val symbols = DateFormatSymbols(Locale("es", "ES"))
        val nombreMes = symbols.months[mes]
        nombreTextView.text = tarea.getNombre()
        planificacionTextView.text = dia + " de " + nombreMes + " de " + year

        return view
    }
}
