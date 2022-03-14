package com.application.shoprye.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.application.shoprye.dao.ShoppingCartDao
import com.application.shoprye.data.RyeJobDatabase
import com.application.shoprye.models.ShoppingCartEntry
import com.application.shoprye.utilities.testCalendar
import com.application.shoprye.utilities.testRyeJob
import com.application.shoprye.utilities.testRyeJobs
import com.application.shoprye.utilities.testShoppingCartEntry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.*

class ShoppingCartEntryDaoTest {
    private lateinit var database: RyeJobDatabase
    private lateinit var shoppingCartDao: ShoppingCartDao
    private var testShoppingCartEntryId: Long = 0

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, RyeJobDatabase::class.java).build()
        shoppingCartDao = database.shoppingCartDao()

        database.ryeJobDao().insertAll(testRyeJobs)
        testShoppingCartEntryId = shoppingCartDao.insertShoppingCartEntry(testShoppingCartEntry)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetShoppingCartEntries() = runBlocking {
        val shoppingCartEntry = ShoppingCartEntry(
            testRyeJobs[1].id,
            testCalendar
        ).also { it.shoppingCartEntryId = 2 }
        shoppingCartDao.insertShoppingCartEntry(shoppingCartEntry)
        ViewMatchers.assertThat(shoppingCartDao.getShoppingCartEntries().first().size, CoreMatchers.equalTo(2))
    }

    @Test
    fun testDeleteShoppingCartEntry() = runBlocking {
        val shoppingCartEntry = ShoppingCartEntry(
            testRyeJobs[1].id,
            testCalendar
        ).also { it.shoppingCartEntryId = 2 }
        shoppingCartDao.insertShoppingCartEntry(shoppingCartEntry)
        ViewMatchers.assertThat(shoppingCartDao.getShoppingCartEntries().first().size, CoreMatchers.equalTo(2))
        shoppingCartDao.deleteShoppingCartEntry(shoppingCartEntry)
        ViewMatchers.assertThat(shoppingCartDao.getShoppingCartEntries().first().size, CoreMatchers.equalTo(1))
    }

    @Test
    fun testGetShoppingCartEntryForJob() = runBlocking {
        Assert.assertTrue(shoppingCartDao.isInCart(testRyeJob.id).first())
    }

    @Test
    fun testGetShoppingCartEntryForJob_notFound() = runBlocking {
        Assert.assertFalse(shoppingCartDao.isInCart(testRyeJobs[2].id).first())
    }

    @Test
    fun testGetRyeJobAndShoppingCartEntry() = runBlocking {
        val ryeJobAndShoppingCartEntry = shoppingCartDao.getRyeJobAndShoppingCartEntries().first()
        ViewMatchers.assertThat(ryeJobAndShoppingCartEntry.size, CoreMatchers.equalTo(1))

        /**
         * Only the [testRyeJob] has been added to the shopping cart, and thus has an associated [ShoppingCartEntry]
         */
        ViewMatchers.assertThat(ryeJobAndShoppingCartEntry[0].ryeJob, CoreMatchers.equalTo(testRyeJob))
        ViewMatchers.assertThat(ryeJobAndShoppingCartEntry[0].shoppingCartEntryList.size, CoreMatchers.equalTo(1))
        ViewMatchers.assertThat(ryeJobAndShoppingCartEntry[0].shoppingCartEntryList[0], CoreMatchers.equalTo(
            testShoppingCartEntry))
    }
}
