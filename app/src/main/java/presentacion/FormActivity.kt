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
    val msg_calendarioInvalido = "La fecha planificada esta ocupada"
    val msg_FechaInvalida = "No es posible crear tareas planificadas para el pasado"
    private lateinit var nombreTarea: EditText
    private lateinit var categoriaTarea: EditText
    private lateinit var descripcionTarea: EditText
    private lateinit var numberPickerHoras: NumberPicker
    private lateinit var numberPickerMinutos: NumberPicker
    val msg_duracion_minima = "La tarea debe durar mínimo un minuto"

    private lateinit var myCheckBox : CheckBox
    private lateinit var calendarioPlan : CalendarView
    private lateinit var timePicker : TimePicker
    //Inicializas la fecha a la actual para que el usuario pueda planificar fecha en el dia de hoy sin tocar el calendario
    private lateinit var calendar : Calendar
    //me guardo la fecha y hora actual del sistema para prevenir que no se hagan planificaciones en el pasado
    private  var year :Int = Calendar.getInstance().get(Calendar.YEAR)
    private  var month :Int  = Calendar.getInstance().get(Calendar.MONTH) +1
    private  var dayOfMonth :Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        nombreTarea = findViewById<EditText>(R.id.NameField)
        categoriaTarea = findViewById(R.id.CategoryField)
        descripcionTarea = findViewById(R.id.DescriptionField)
        numberPickerHoras = findViewById<NumberPicker>(R.id.numberPickerHours)
        numberPickerMinutos = findViewById<NumberPicker>(R.id.numberPickerMinutes)

        timePicker = findViewById<TimePicker>(R.id.timePicker)
        timePicker.setIs24HourView(true)

        calendarioPlan = findViewById<CalendarView>(R.id.calendarView)
        myCheckBox = findViewById<CheckBox>(R.id.checkBox)
        calendar = Calendar.getInstance()


        setUpNumberPickers()
        setVisibilityDate()
        readCalendar()
    }


    fun sendFormButton (buttonToForm: View) {

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

            if(numberPickerHoras.value != 0){
                numberPickerHoras.value--;
            }
            if(numberPickerMinutos.value != 0){
                numberPickerMinutos.value--;
            }

            t = Tarea(
                nombreTarea.text.toString(),
                categoriaTarea.text.toString(),
                numberPickerHoras.value-1,
                numberPickerMinutos.value,
                descripcionTarea.text.toString(),
            )
            CoroutineScope(Dispatchers.IO).launch {
                if (myCheckBox.isChecked()) {
                    t.setPlan(calendar)
                } else {
                    //Si entra aqui tendra que buscarle un lugar
                    t.setPlanNull()
                }

                if (myCheckBox.isChecked() && (!t.planificar() || !fechaValida()) ) {
                    if(!fechaValida()){
                        runOnUiThread {
                            Toast.makeText(applicationContext, msg_FechaInvalida, Toast.LENGTH_LONG)
                                .show()
                        }
                    }else{
                        runOnUiThread {
                            Toast.makeText(applicationContext, msg_calendarioInvalido, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                } else {
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

    }

    private fun setVisibilityDate(){
        calendarioPlan.setVisibility(View.GONE)
        timePicker.setVisibility(View.GONE)
        myCheckBox.setOnCheckedChangeListener { checkBox, isChecked ->

            if (isChecked) {
                calendarioPlan.setVisibility(View.VISIBLE)
                timePicker.setVisibility(View.VISIBLE)

                timePicker.hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                timePicker.minute=Calendar.getInstance().get(Calendar.MINUTE)
            } else {
                calendarioPlan.setVisibility(View.GONE)
                timePicker.setVisibility(View.GONE)
            }
        }
    }

    private fun readCalendar(){  //creo la fecha con la fecha indicada en el calendario


        calendarioPlan.setOnDateChangeListener { _, yearR, monthR, dayOfMonthR ->
            calendar.set(Calendar.YEAR, yearR)
            calendar.set(Calendar.MONTH, monthR)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonthR)
        }
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
            calendar.set(Calendar.MINUTE,minute)
        }
    }

    private fun fechaValida() :Boolean{

        return calendar.get(Calendar.YEAR)>=year && (calendar.get(Calendar.MONTH)+1 > month || (calendar.get(Calendar.MONTH)+1== month && calendar.get(Calendar.DAY_OF_MONTH)>=dayOfMonth))
    }

}