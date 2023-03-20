package presentacion

import negocio.Tarea
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.get
import com.example.myapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.view.isVisible
import java.util.*

class FormActivity : AppCompatActivity() {

    val msg_exito = "Tarea creada con éxito"
    val msg_formImcompleto = "Formulario incompleto"
    val msg_duplicado = "Tarea ya existe"
    private lateinit var nombreTarea: EditText
    private lateinit var categoriaTarea: EditText
    private lateinit var descripcionTarea: EditText
    private lateinit var numberPickerHoras: NumberPicker
    private lateinit var numberPickerMinutos: NumberPicker
    val msg_duracion_minima = "La tarea debe durar mínimo un minuto"

    private lateinit var numberPickerPlanHoras: NumberPicker
    private lateinit var numberPickerPlanMinutos: NumberPicker

    private lateinit var myCheckBox : CheckBox
    private lateinit var calendarioPlan : CalendarView

    private var fechaSeleccionada: Date? = null
    //Inicializas la fecha a la actual para que el usuario pueda planificar fecha en el dia de hoy sin tocar el calendario
    private  var dayOfMonth :Int = Date().day
    private  var month :Int  = Date().month
    private  var year :Int = Date().year+1900

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        nombreTarea = findViewById<EditText>(R.id.NameField)
        categoriaTarea = findViewById(R.id.CategoryField)
        descripcionTarea = findViewById(R.id.DescriptionField)
        numberPickerHoras = findViewById<NumberPicker>(R.id.numberPickerHours)
        numberPickerMinutos = findViewById<NumberPicker>(R.id.numberPickerMinutes)

        numberPickerPlanHoras =  findViewById<NumberPicker>(R.id.numberPickerHoraPlan)
        numberPickerPlanMinutos= findViewById<NumberPicker>(R.id.numberPickerMinPlan)

        calendarioPlan = findViewById<CalendarView>(R.id.calendarView)
        myCheckBox = findViewById<CheckBox>(R.id.checkBox)

        setUpNumberPickers()
        setVisibilityDate()
        readCalendar()
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
        if (numberPickerHoras.value == 0 && numberPickerMinutos.value == 0) {
            Toast.makeText(applicationContext, msg_duracion_minima , Toast.LENGTH_LONG).show()
            valido = false
        }
        if (!valido)
            Toast.makeText(applicationContext, msg_formImcompleto , Toast.LENGTH_LONG).show()
        else {
            Toast.makeText(applicationContext, msg_exito , Toast.LENGTH_LONG).show()

            if(myCheckBox.isChecked()){
                fechaSeleccionada = Date(year, month, dayOfMonth,numberPickerPlanHoras.value, numberPickerPlanMinutos.value)
            }

            var t = Tarea(nombreTarea.text.toString(), categoriaTarea.text.toString(), numberPickerHoras.value, numberPickerMinutos.value, descripcionTarea.text.toString(),fechaSeleccionada)

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
        numberPickerMinutos.minValue = 0
        numberPickerMinutos.maxValue = 60
        numberPickerMinutos.wrapSelectorWheel = true


        numberPickerPlanMinutos.minValue = 0
        numberPickerPlanMinutos.maxValue = 60
        numberPickerPlanMinutos.wrapSelectorWheel = true

        numberPickerPlanHoras.minValue = 0
        numberPickerPlanHoras.maxValue = 23
        numberPickerPlanHoras.wrapSelectorWheel = true

    }

    private fun setVisibilityDate(){
        calendarioPlan.setVisibility(View.GONE)
        numberPickerPlanHoras.setVisibility(View.GONE)
        numberPickerPlanMinutos.setVisibility(View.GONE)
        findViewById<TextView>(R.id.textHoraPlan).setVisibility(View.GONE)
        findViewById<TextView>(R.id.textPuntos).setVisibility(View.GONE)
        myCheckBox.setOnCheckedChangeListener { checkBox, isChecked ->

            if (isChecked) {
                calendarioPlan.setVisibility(View.VISIBLE)
                numberPickerPlanHoras.setVisibility(View.VISIBLE)
                numberPickerPlanMinutos.setVisibility(View.VISIBLE)
                findViewById<TextView>(R.id.textHoraPlan).setVisibility(View.VISIBLE)
                findViewById<TextView>(R.id.textPuntos).setVisibility(View.VISIBLE)

            } else {
                calendarioPlan.setVisibility(View.GONE)
                numberPickerPlanHoras.setVisibility(View.GONE)
                numberPickerPlanMinutos.setVisibility(View.GONE)
                findViewById<TextView>(R.id.textHoraPlan).setVisibility(View.GONE)
                findViewById<TextView>(R.id.textPuntos).setVisibility(View.GONE)
            }


        }
    }

    private fun readCalendar(){
        calendarioPlan.setOnDateChangeListener { _, year, month, dayOfMonth ->
            this.year = year -1900
            this.month=month
            this.dayOfMonth = dayOfMonth
        }
    }


}

