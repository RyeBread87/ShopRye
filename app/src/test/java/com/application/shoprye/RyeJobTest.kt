package com.application.shoprye

import com.application.shoprye.models.RyeJob
import com.application.shoprye.utils.Converters
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RyeJobTest {

    private lateinit var sampleRyeJob: RyeJob
    private lateinit var defaultRyeJob: RyeJob

    companion object {
        const val AIRFARE_COST = "$1,500.00"
    }

    @Before
    fun setUp() {
        sampleRyeJob = RyeJob(1, "Rye Does Your Homework", "Math problems, an essay, group project, " +
                "you name it! Rye will do it on your behalf and hand it in on time. Grade of F or better, guaranteed.",
            "https://www.google.com/", true, "98765.43")
        defaultRyeJob = RyeJob(2, "Rye Waters Your Plants", "Rye will water your plants" +
                "while you're away. He will protect them as if they were his own children.")
    }

    @Test
    fun test_defaultValues() {
        assertEquals("http://theryebread.com/Content/no_image_available.jpg", defaultRyeJob.imageUrl)
        assertEquals(false, defaultRyeJob.requiresTravel)
        assertEquals("$98,765.43", defaultRyeJob.cost)
    }

    private fun getTotalCost(ryeJob: RyeJob): Double {
        return if (!ryeJob.requiresTravel) Converters.stringCostToDouble(ryeJob.cost)
            else Converters.stringCostToDouble(ryeJob.cost) + Converters.stringCostToDouble(AIRFARE_COST)
    }

    private fun canAfford(ryeJob: RyeJob, budget: Double): Boolean {
        return (getTotalCost(ryeJob) <= budget)
    }

    @Test
    fun test_canAffordRyeJob() {
        var budget = 90000.toDouble()

        // assert that the jobs we're comparing share the same base cost
        assertTrue(
            Converters.stringCostToDouble(sampleRyeJob.cost) == Converters.stringCostToDouble(defaultRyeJob.cost)
        )

        // test for not enough budget for base cost
        assertTrue(
            !canAfford(sampleRyeJob, budget) && !canAfford(defaultRyeJob, budget)
        )

        budget += 10000.toDouble()
        // test for enough budget for base cost, but not enough when accounting for airfare
        assertTrue(
            !canAfford(sampleRyeJob, budget) && canAfford(defaultRyeJob, budget)
        )

        // test for enough budget for base cost plus airfare
        budget += 10000.toDouble()
        assertTrue(
            canAfford(sampleRyeJob, budget) && canAfford(defaultRyeJob, budget)
        )
    }

    @Test
    fun test_toString() {
        assertEquals("Rye Does Your Homework", sampleRyeJob.toString())
    }
}