package com.application.shoprye.utilities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import com.application.shoprye.models.RyeJob
import com.application.shoprye.models.ShoppingCartEntry
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import java.util.*

/**
 * [RyeJob] objects used for tests.
 */
val testRyeJobs = arrayListOf(
    RyeJob(1, "Apple", "A red fruit", "www.example1.com"),
    RyeJob(2, "B", "Description B", "www.example2.com"),
    RyeJob(3, "C", "Description C", "www.example3.com")
)
val testRyeJob = testRyeJobs[0]

/**
 * [Calendar] object used for tests.
 */
val testCalendar: Calendar = Calendar.getInstance().apply {
    set(Calendar.YEAR, 1998)
    set(Calendar.MONTH, Calendar.SEPTEMBER)
    set(Calendar.DAY_OF_MONTH, 4)
}

/**
 * [ShoppingCartEntry] object used for tests.
 */
val testShoppingCartEntry = ShoppingCartEntry(testRyeJob.id, testCalendar)

/**
 * Simplify testing Intents with Chooser
 *
 * @param matcher the actual intent before wrapped by Chooser Intent
 */
fun chooser(matcher: Matcher<Intent>): Matcher<Intent> = Matchers.allOf(
    hasAction(Intent.ACTION_CHOOSER),
    hasExtra(Matchers.`is`(Intent.EXTRA_INTENT), matcher)
)