package com.application.shoprye

import com.application.shoprye.utils.Converters
import org.junit.Assert
import org.junit.Test

class ConverterTest {

    @Test
    fun test_convertStringCostAndBack() {
        val costString = "$98,765.43"
        val costDouble = Converters.stringCostToDouble(costString)
        Assert.assertEquals(costString, Converters.doubleCostToString(costDouble))
    }

    @Test
    fun test_convertDoubleCostAndBack() {
        val costDouble = 98765.43
        val costString = Converters.doubleCostToString(costDouble)
        Assert.assertTrue(costDouble == Converters.stringCostToDouble(costString))
    }
}