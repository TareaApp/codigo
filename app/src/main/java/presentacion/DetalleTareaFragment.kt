package presentacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.R
import negocio.Tarea

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetalleTareaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetalleTareaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var tarea: Tarea? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragmen


        return inflater.inflate(R.layout.fragment_detalle_tarea, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        view.findViewById<TextView>(R.id.nombreTareaDetalles).text = tarea?.getNombre() ?: "Nombre Tarea"
        view.findViewById<TextView>(R.id.categoriaDetalles).text = tarea?.getAsignatura() ?: "Nombre Asignatura"
        view.findViewById<TextView>(R.id.descripcionDetalles).text = "Descripcion de la tarea: \n" + tarea?.getDescription() ?: "Descripcion"
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetalleTareaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Tarea?) =
            DetalleTareaFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    fun setTarea(tarea: Tarea?) {
        this.tarea = tarea
    }
}