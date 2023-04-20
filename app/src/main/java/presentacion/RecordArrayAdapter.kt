package presentacion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.myapplication.R
import negocio.Recordatorio
import negocio.Tarea
import java.text.DateFormatSymbols
import java.util.*

class RecordArrayAdapter(context: Context, private val resource: Int, private val records: ArrayList<Recordatorio>) :
    ArrayAdapter<Recordatorio>(context, resource, records) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val recordatorio = records[position]

        val nombreTextView = view.findViewById<TextView>(R.id.recordatorioNombre)
        val asignaturaTextView = view.findViewById<TextView>(R.id.recordatorioAsignatura)
        val notifiacionTextView = view.findViewById<TextView>(R.id.recordatorioNotificacion)

        val fecha = recordatorio.getFecha()
        val dia = fecha.get(Calendar.DAY_OF_MONTH).toString()
        val mes = fecha.get(Calendar.MONTH)
        val year = fecha.get(Calendar.YEAR).toString()
        val hora = fecha.get(Calendar.HOUR_OF_DAY).toString()
        val minuto = fecha.get(Calendar.MINUTE).toString()
        val symbols = DateFormatSymbols(Locale("es", "ES"))
        val nombreMes = symbols.months[mes]

        nombreTextView.text = recordatorio.getNombre()
        asignaturaTextView.text = recordatorio.getCategoria()
        notifiacionTextView.text = dia + " de " + nombreMes + " de " + year +"  "+ hora+":"+minuto

        return view
    }
}
