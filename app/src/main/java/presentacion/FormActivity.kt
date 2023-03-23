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

    private lateinit var t :Tarea
    val msg_exito = "Tarea creada con éxito"
    val msg_formImcompleto = "Formulario incompleto"
    val msg_duplicado = "Tarea ya existe"
    val msg_calendarioInvalido = "La fecha planificada esta ocupada o ya ha pasado"
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

    private lateinit var fechaSeleccionada: Calendar
    //Inicializas la fecha a la actual para que el usuario pueda planificar fecha en el dia de hoy sin tocar el calendario
    private lateinit var calendar : Calendar
    //me guardo la fecha actual del sistema para prevenir que no se hagan planificaciones en el pasado
    private  var year :Int = Calendar.getInstance().get(Calendar.YEAR)
    private  var month :Int  = Calendar.getInstance().get(Calendar.MONTH)
    private  var dayOfMonth :Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

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
        calendar = Calendar.getInstance()

        setUpNumberPickers()
        setVisibilityDate()
        readCalendar()
    }


    suspend fun sendFormButton (buttonToForm: View) {

        var valido = true;

        if (nombreTarea.text.toString().trim() == "") {
            nombreTarea.error = "Requerido"
            valido = false
        }
        if (categoriaTarea.text.toString().trim() == "") {
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
            t = Tarea(
                nombreTarea.text.toString(),
                categoriaTarea.text.toString(),
                numberPickerHoras.value,
                numberPickerMinutos.value,
                descripcionTarea.text.toString(),
            )

            if (myCheckBox.isChecked()) {
                //Las horas las guarda mal
               t.setPlan(calendar)
            } else {
                //Si entra aqui tendra que buscarle un lugar
                t.setPlanNull()
            }

            if (myCheckBox.isChecked() && (!t.planificar() || !fechaValida()) ) {
                Toast.makeText(applicationContext, msg_calendarioInvalido, Toast.LENGTH_LONG).show()
            } else {
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
                    } else {
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
    }

    private fun setUpNumberPickers (){

        numberPickerHoras.minValue = 0
        numberPickerHoras.maxValue = 10
        numberPickerHoras.wrapSelectorWheel = true


        /* Me invento que como mínimo la tarea tiene que durar un minuto porque si no habría tareas con 0 minutos y 0 horas*/
        numberPickerMinutos.minValue = 0
        numberPickerMinutos.maxValue = 59
        numberPickerMinutos.wrapSelectorWheel = true


        numberPickerPlanMinutos.minValue = 0
        numberPickerPlanMinutos.maxValue = 59
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

    private fun readCalendar(){  //creo la fecha con la fecha indicada en el calendario


        calendarioPlan.setOnDateChangeListener { _, yearR, monthR, dayOfMonthR ->

            calendar.set(Calendar.YEAR, yearR)
            calendar.set(Calendar.MONTH, monthR)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonthR)

        }
    }

    private fun fechaValida() :Boolean{
        return calendar.get(Calendar.YEAR)>=year && calendar.get(Calendar.MONTH) >= month && calendar.get(Calendar.DAY_OF_MONTH)>=dayOfMonth
    }

}

