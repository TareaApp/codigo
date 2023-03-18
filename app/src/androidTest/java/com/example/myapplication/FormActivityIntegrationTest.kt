import android.view.View
import android.widget.NumberPicker
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Description
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import presentacion.FormActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.Matcher


class FormActivityIntegrationTest {
    private lateinit var scenario: ActivityScenario<FormActivity>


    private fun setValue(value: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "Set the value of a NumberPicker"

            override fun getConstraints() = isAssignableFrom(NumberPicker::class.java)

            override fun perform(uiController: UiController, view: View) {
                (view as NumberPicker).value = value
            }
        }
    }

    fun withNumberPickerValue(expectedValue: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with value $expectedValue")
            }

            override fun matchesSafely(item: View): Boolean {
                if (item !is NumberPicker) {
                    return false
                }
                return item.value == expectedValue
            }
        }
    }

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(FormActivity::class.java)
    }
    /*
    @Test
    fun testFormFields() {
        onView(withId(R.id.NameField)).perform(scrollTo(), clearText(), typeText("Tarea de prueba"))
            .check(matches(allOf(isDisplayed(), withText("Tarea de prueba"), hasFocus())))
        onView(withId(R.id.CategoryField)).perform(scrollTo(), clearText(), typeText("Categoria de prueba"))
            .check(matches(allOf(isDisplayed(), withText("Categoria de prueba"), hasFocus())))
        onView(withId(R.id.numberPickerHours)).perform(scrollTo(), setValue(1))
            .check(matches(allOf(isDisplayed(), withEffectiveVisibility(Visibility.VISIBLE), withNumberPickerValue(1))))
        onView(withId(R.id.numberPickerMinutes)).perform(scrollTo(), setValue(30))
            .check(matches(allOf(isDisplayed(), withEffectiveVisibility(Visibility.VISIBLE), withNumberPickerValue(30))))
    }

    @Test
    fun testFormSubmission() {
        onView(withId(R.id.NameField)).perform(scrollTo(), clearText(), typeText("Tarea de prueba"))
        onView(withId(R.id.CategoryField)).perform(scrollTo(), clearText(), typeText("Categoria de prueba"))
        onView(withId(R.id.numberPickerHours)).perform(scrollTo(), setValue(1))
        onView(withId(R.id.numberPickerMinutes)).perform(scrollTo(), setValue(30))

        // TODO: Add assertions for verifying the form submission
    }

     */
}