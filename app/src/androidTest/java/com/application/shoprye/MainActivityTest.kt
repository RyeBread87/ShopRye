package com.application.shoprye

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class MainActivityTest {

    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)

    @Test
    fun clickAddRyeJob_OpensRyeJobList() {
        // Note - this test only works when the shopping cart is empty

        // When the "Add To Shopping Cart?" button is clicked
        onView(withId(R.id.add_rye_job)).perform(click())

        Thread.sleep(3000)
        // Then the ViewPager should change to the Rye Jobs List page
        onView(withId(R.id.rye_job_recyclerview))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun clickRyeJobList_OpensRyeJobList() {

        // When the "Rye Jobs List" tab is clicked
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))

        Thread.sleep(3000)
        // Then the ViewPager should change to the Rye Jobs List page
        onView(withId(R.id.rye_job_recyclerview))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() = AllOf.allOf(
                isDisplayed(),
                ViewMatchers.isAssignableFrom(TabLayout::class.java)
            )

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }
}