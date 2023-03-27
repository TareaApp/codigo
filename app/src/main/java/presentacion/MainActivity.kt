package presentacion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    // Es la actividad que se inicia al abrir la app.
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navbar = findViewById<BottomNavigationView>(R.id.navigationView)
        navbar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navbar_calendario -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, CalendarioFragment())
                        .commit()
                    true
                }
                R.id.navbar_horario -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, HorarioFragment())
                        .commit()
                    true
                }
                R.id.navbar_recordatorio -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, RecordatoriosFragment())
                        .commit()
                    true
                }
                R.id.navbar_lista -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, TaskListFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }



}