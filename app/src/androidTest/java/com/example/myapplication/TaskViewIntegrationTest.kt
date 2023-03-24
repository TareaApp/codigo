package com.example.myapplication

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.junit.Test

class TaskListViewIntegrationTest {

    //private lateinit var scenario: ActivityScenario<ButtonFragment>

    //@Before
    //fun setUp() {
        //scenario = ActivityScenario.launch(ButtonFragment::class.java)
    //}

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