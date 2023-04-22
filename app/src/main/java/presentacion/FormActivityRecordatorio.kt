package presentacion

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import integracion.MyNotificationReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import negocio.Recordatorio
import java.util.*

class FormActivityRecordatorio : AppCompatActivity(){

    private lateinit var r :Recordatorio;
    private lateinit var nombreRec: EditText
    private lateinit var categoriaRec: EditText
    private lateinit var descripcionRec: EditText
    private lateinit var calendarioPlan : CalendarView
    private lateinit var calendar : Calendar
    private lateinit var timePicker : TimePicker

    val msg_exito = "Recordatorio creado con éxito"
    val msg_formImcompleto = "Formulario incompleto"
    val msg_duplicado = "El recordatorio ya existe"
    val msg_FechaInvalida = "No es posible crear recordatorios para el pasado"

    // FECHA Y HORA ACTUALES DEL SISTEMA
    private  var year :Int = Calendar.getInstance().get(Calendar.YEAR)
    private  var month :Int  = Calendar.getInstance().get(Calendar.MONTH) +1
    private  var dayOfMonth :Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formrecordatorio)

        nombreRec = findViewById<EditText>(R.id.NameFieldRec)
        categoriaRec = findViewById(R.id.CategoryFieldRec)
        descripcionRec = findViewById(R.id.DescriptionFieldRec)
        timePicker = findViewById(R.id.timePickerRec)
        timePicker.setIs24HourView(true)
        calendarioPlan = findViewById<CalendarView>(R.id.calendarViewRec)
        calendar = Calendar.getInstance()

        readCalendar()
    }

    private fun readCalendar(){

        calendarioPlan.setOnDateChangeListener { _, yearR, monthR, dayOfMonthR ->
            calendar.set(Calendar.YEAR, yearR)
            calendar.set(Calendar.MONTH, monthR)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonthR)
        }
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
            calendar.set(Calendar.MINUTE,minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        }
    }

    private fun fechaValida() :Boolean{
        return calendar.get(Calendar.YEAR)>=year && (calendar.get(Calendar.MONTH)+1 > month || (calendar.get(Calendar.MONTH)+1== month && calendar.get(Calendar.DAY_OF_MONTH)>=dayOfMonth))
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun notifica(rr : Recordatorio) : Unit{

        val calendar2 = Calendar.getInstance().apply {//esto es de prueba, sustiruye calendar2.timeInMillis
            set(Calendar.YEAR, 2023)                             //a rr.getFecha().timeInMillis o calendar.timeInMillis
            set(Calendar.MONTH, 3) // Note: Month is zero-based (i.e. January is 0)
            set(Calendar.DAY_OF_MONTH, 22)
            set(Calendar.HOUR_OF_DAY, 14)
            set(Calendar.MINUTE, 3)
            set(Calendar.SECOND, 0)
        }

        val intent = Intent(this, MyNotificationReceiver::class.java).apply {
            putExtra("channelId"+rr.getNombre(), "my_channel_id"+rr.getNombre())
            putExtra("title", rr.getNombre())
            putExtra("message", "La tarea "+rr.getNombre()+" esta a punto de empezar")
        }

        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar2.timeInMillis, pendingIntent)
    }
    fun sendFormButtonRec (buttonToForm: View){

        var valido = true;
        var tmp = false;

        if (nombreRec.text.toString().trim() == "") {
            nombreRec.error = "Requerido"
            valido = false
        }
        if (categoriaRec.text.toString().trim() == "") {
            categoriaRec.error = "Requerido"
            valido = false
        }

        if (!valido) {
            Toast.makeText(applicationContext, msg_formImcompleto, Toast.LENGTH_LONG).show()
        }
        else {
            r = Recordatorio (
                nombreRec.text.toString(),
                categoriaRec.text.toString(),
                descripcionRec.text.toString(),
                calendar
            )

            CoroutineScope(Dispatchers.IO).launch {

                if (!fechaValida()){
                    runOnUiThread {
                        Toast.makeText(applicationContext, msg_FechaInvalida, Toast.LENGTH_LONG)
                            .show()
                    }
                } else {

                    if (r.existe()) {
                        runOnUiThread {
                            Toast.makeText(applicationContext, msg_duplicado, Toast.LENGTH_LONG)
                                .show()
                        }
                    } else {
                        notifica(r)
                        r.guardar()

                        runOnUiThread {
                            Toast.makeText(applicationContext, msg_exito, Toast.LENGTH_LONG)
                                .show()
                        }
                        finish()
                    }
                }
            }
        }
    }
}