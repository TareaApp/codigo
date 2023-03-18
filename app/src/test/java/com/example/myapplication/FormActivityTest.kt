package presentacion
import android.view.View
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

internal class FormActivityTest {

    @RelaxedMockK
    private lateinit var buttonToForm : View

    private var formActivity = mockk<FormActivity>(relaxed = true)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `el nombre de la tarea es vacio`() = runBlocking{
        //Given
        formActivity.sendFormButton(buttonToForm)

        //When
        every { formActivity.nombreTarea.text.toString() == "" } returns true
        //Then
        val error = formActivity.nombreTarea.error // Leer el error del EditText

        assertEquals("Requerido", error.toString())
    }



    @Test
    fun `el nombre de la categoria es vacio`() = runBlocking{
        formActivity.sendFormButton(buttonToForm)

        //  println(formActivity.categoriaTarea.text.toString() + "Requerido")
        every { formActivity.categoriaTarea.text.toString() } returns ""

        assertEquals("CharSequence(child^2 of #6#8#10)", formActivity.categoriaTarea.error.toString())


    }
}