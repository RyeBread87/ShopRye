package com.application.shoprye

import com.application.shoprye.models.ShoppingCartEntry
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import java.util.*

class ShoppingCartTest {

    @Test
    fun testDefaultValues() {
        val shoppingCartEntry = ShoppingCartEntry(1)
        val cal = Calendar.getInstance()
        assertYMD(cal, shoppingCartEntry.addedToCartDate)
        Assert.assertEquals(0L, shoppingCartEntry.shoppingCartEntryId)
    }

    // Only Year/Month/Day precision is needed for comparing Calendar entries
    private fun assertYMD(expectedCal: Calendar, actualCal: Calendar) {
        Assert.assertThat(
            actualCal.get(Calendar.YEAR),
            CoreMatchers.equalTo(expectedCal.get(Calendar.YEAR))
        )
        Assert.assertThat(
            actualCal.get(Calendar.MONTH),
            CoreMatchers.equalTo(expectedCal.get(Calendar.MONTH))
        )
        Assert.assertThat(
            actualCal.get(Calendar.DAY_OF_MONTH),
            CoreMatchers.equalTo(expectedCal.get(Calendar.DAY_OF_MONTH))
        )
    }
}