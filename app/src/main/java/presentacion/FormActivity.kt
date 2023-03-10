package presentacion

import negocio.Tarea
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import com.example.myapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormActivity : AppCompatActivity() {

    val msg_exito = "Tarea creada con éxito"
    val msg_formImcompleto = "Formulario incompleto"
    val msg_duplicado = "Tarea ya existe"
    private lateinit var nombreTarea: EditText
    private lateinit var categoriaTarea: EditText
    private lateinit var descripcionTarea: EditText
    private lateinit var numberPickerHoras: NumberPicker
    private lateinit var numberPickerMinutos: NumberPicker



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        nombreTarea = findViewById<EditText>(R.id.NameField)
        categoriaTarea = findViewById(R.id.CategoryField)
        descripcionTarea = findViewById(R.id.DescriptionField)
        numberPickerHoras = findViewById<NumberPicker>(R.id.numberPickerHours)
        numberPickerMinutos = findViewById<NumberPicker>(R.id.numberPickerMinutes)
        setUpNumberPickers()
    }

    fun sendFormButton (buttonToForm: View) {

        // Se llama cuando se pulsa el botón Añadir, ahora se procede a validar el formulario:

        /* Esto de coger las partes del xml con el findViewByID no es muy buena opción, es mejor
        * hacerlo con ViewBinding, que te guarda la vista en una variable y accedes a las cosas
        * como si fueran atributos (refactorizarlo más adelante) */



        /*
        val msg_incompleto = Snackbar.make(buttonToForm, "Formulario incompleto", LENGTH_LONG * 20)
        val msg_exito = Snackbar.make(buttonToForm, "Tarea creada con éxito", LENGTH_LONG * 20)
        val msg_duplicada = Snackbar.make(buttonToForm, "Tarea ya existe", LENGTH_LONG * 20)
    */
        var valido = true;

        if (nombreTarea.text.toString() == "") {
            nombreTarea.error = "Requerido"
            valido = false
        }
        if (categoriaTarea.text.toString() == "") {
            categoriaTarea.error = "Requerido"
            valido = false
        }
        /*
        if (descripcionTarea.text.toString() == "") {
            descripcionTarea.error = "Requerido"
            valido = false
        }
            */
        if (!valido)
            Toast.makeText(applicationContext, msg_formImcompleto , Toast.LENGTH_LONG).show()
        else {
            var t = Tarea(nombreTarea.text.toString(), categoriaTarea.text.toString(), numberPickerHoras.value, numberPickerMinutos.value, descripcionTarea.text.toString())
            CoroutineScope(Dispatchers.IO).launch {
                if (!t.existe()) {
                    t.guardar()
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext, msg_exito,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    finish()
                }
                else{
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            msg_duplicado,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }

        }

    }

    private fun setUpNumberPickers (){

        numberPickerHoras.minValue = 0
        numberPickerHoras.maxValue = 100
        numberPickerHoras.wrapSelectorWheel = true


        /* Me invento que como mínimo la tarea tiene que durar un minuto porque si no habría tareas con 0 minutos y 0 horas*/
        numberPickerMinutos.minValue = 1
        numberPickerMinutos.maxValue = 60
        numberPickerMinutos.wrapSelectorWheel = true

    }

}

