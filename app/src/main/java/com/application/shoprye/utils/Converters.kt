package com.application.shoprye.utils

import java.text.NumberFormat

class Converters {

    companion object {

        fun stringCostToDouble(stringCost: String): Double {
            var inputString = stringCost
            for (i in stringCost.indices) {
                if (!Character.isDigit(stringCost[i]) && stringCost[i] != '.') {
                    inputString = inputString.replace(stringCost[i].toString(), "")
                }
            }
            return inputString.toDouble()
        }

        fun doubleCostToString(doubleCost: Double): String {
            val format = NumberFormat.getCurrencyInstance()
            return format.format(doubleCost)
        }
    }
}