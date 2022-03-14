package com.application.shoprye.repositories

import com.application.shoprye.dao.ShoppingCartDao
import com.application.shoprye.models.ShoppingCartEntry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingCartRepository @Inject constructor(
    private val shoppingCartDao: ShoppingCartDao
    ) {

    suspend fun createShoppingCartEntry(ryeJobId: Int) {
        val shoppingCartEntry = ShoppingCartEntry(ryeJobId)
        shoppingCartDao.insertShoppingCartEntry(shoppingCartEntry)
    }

    suspend fun removeAllShoppingCartEntries() {
        shoppingCartDao.deleteAllShoppingCartEntries()
    }

    fun isInCart(ryeJobId: Int) = shoppingCartDao.isInCart(ryeJobId)

    fun getShoppingCartEntries() = shoppingCartDao.getRyeJobAndShoppingCartEntries()
}