package presentacion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import negocio.Recordatorio
import negocio.Tarea

class RecordatoriosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recordatorios, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView = view.findViewById<ListView>(R.id.listView)
        val taskList = Recordatorio.listarTodas()

        if(taskList.isNotEmpty()){
            val adapter = RecordArrayAdapter(view.context, R.layout.rec_item, taskList)
            listView.adapter = adapter
        }
        else{
            Toast.makeText(view.context, "No hay recordatorios", Toast.LENGTH_LONG).show()
        }

        view.findViewById<Button>(R.id.buttonToFormRecordatorio)?.setOnClickListener {
            startActivity(Intent(requireContext(), FormActivityRecordatorio::class.java))
        }

    }
}