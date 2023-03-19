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

    private lateinit var numberPickerPlanMes: NumberPicker
    private lateinit var numberPickerPlanDia: NumberPicker
    private lateinit var numberPickerPlanHora: NumberPicker
    private lateinit var numberPickerPlanMinutos: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        nombreTarea = findViewById<EditText>(R.id.NameField)
        categoriaTarea = findViewById(R.id.CategoryField)
        descripcionTarea = findViewById(R.id.DescriptionField)
        numberPickerHoras = findViewById<NumberPicker>(R.id.numberPickerHours)
        numberPickerMinutos = findViewById<NumberPicker>(R.id.numberPickerMinutes)

        numberPickerPlanMes= findViewById<NumberPicker>(R.id.numberPickerMonth)
        numberPickerPlanDia= findViewById<NumberPicker>(R.id.numberPickerDay)
        numberPickerPlanHora= findViewById<NumberPicker>(R.id.numberPickerHoraPlan)
        numberPickerPlanMinutos= findViewById<NumberPicker>(R.id.numberPickerMinPlan)


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
        if (!valido)
            Toast.makeText(applicationContext, msg_formImcompleto , Toast.LENGTH_LONG).show()
        else {
            Toast.makeText(applicationContext, "que es lo k manin" , Toast.LENGTH_LONG).show()

            var t = Tarea(nombreTarea.text.toString(), categoriaTarea.text.toString(), numberPickerHoras.value, numberPickerMinutos.value, descripcionTarea.text.toString(),  numberPickerPlanMes.value,numberPickerPlanDia.value,numberPickerPlanHora.value,numberPickerPlanMinutos.value)
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

        numberPickerPlanMes.minValue = 0
        numberPickerPlanMes.maxValue = 12
        numberPickerPlanMes.wrapSelectorWheel = true

        numberPickerPlanHora.minValue = 0
        numberPickerPlanHora.maxValue = 23
        numberPickerPlanHora.wrapSelectorWheel = true

        numberPickerPlanDia.minValue = 0
        numberPickerPlanDia.maxValue = 31
        numberPickerPlanDia.wrapSelectorWheel = true

        numberPickerPlanMinutos.minValue = 0
        numberPickerPlanMinutos.maxValue = 60
        numberPickerPlanMinutos.wrapSelectorWheel = true
    }

}

