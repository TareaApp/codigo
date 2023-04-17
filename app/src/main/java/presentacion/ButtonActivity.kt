package presentacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.R

class ButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button)

        val buttonToForm = findViewById<Button>(R.id.buttonToForm)

        buttonToForm.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
        }

    }
}