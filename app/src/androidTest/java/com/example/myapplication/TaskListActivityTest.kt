package com.example.myapplication


import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import presentacion.TaskListActivity

class TaskListActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<TaskListActivity> = ActivityTestRule(TaskListActivity::class.java)

    @Test
    fun testTaskListDisplayed() {
        // Verifica que la lista se puede ver
        Espresso.onView(withId(R.id.listView)).check(matches(isDisplayed()))

        // Verifica que la primera tarea es la correspondiente
        Espresso.onView(withId(android.R.id.text1)).check(matches(withText("Tarea1"))) //poner nombre de la primera tarea
    }

}