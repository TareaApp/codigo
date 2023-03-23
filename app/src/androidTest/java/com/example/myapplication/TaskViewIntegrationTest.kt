package com.example.myapplication

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.junit.Before
import org.junit.Test
import presentacion.ButtonActivity

class TaskListViewIntegrationTest {

    private lateinit var scenario: ActivityScenario<ButtonActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(ButtonActivity::class.java)
    }

    @Test
    fun testTaskListDisplayed() {
        // Verifica que la lista se puede ver
        Espresso.onView(ViewMatchers.withId(R.id.listView)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }
}