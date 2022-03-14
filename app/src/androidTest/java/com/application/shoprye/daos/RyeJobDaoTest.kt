package com.application.shoprye.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.application.shoprye.dao.RyeJobDao
import com.application.shoprye.data.RyeJobDatabase
import com.application.shoprye.models.RyeJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RyeJobDaoTest {
    private lateinit var database: RyeJobDatabase
    private lateinit var ryeJobDao: RyeJobDao
    private val ryeJobA = RyeJob(1, "A", "", "www.example1.com", true, "")
    private val ryeJobB = RyeJob(2, "B", "", "www.example2.com", true, "")
    private val ryeJobC = RyeJob(3, "C", "", "www.example3.com", false, "")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, RyeJobDatabase::class.java).build()
        ryeJobDao = database.ryeJobDao()

        // Insert rye jobs in non-alphabetical order to test that results are sorted by name
        ryeJobDao.insertAll(listOf(ryeJobB, ryeJobC, ryeJobA))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetRyeJobs() = runBlocking {
        val ryeJobsList = ryeJobDao.getAllRyeJobs().first()
        Assert.assertThat(ryeJobsList.size, Matchers.equalTo(3))

        // Ensure rye jobs list is sorted by name
        Assert.assertThat(ryeJobsList[0], Matchers.equalTo(ryeJobA))
        Assert.assertThat(ryeJobsList[1], Matchers.equalTo(ryeJobB))
        Assert.assertThat(ryeJobsList[2], Matchers.equalTo(ryeJobC))
    }

    @Test
    fun testSorting() = runBlocking {
        val ryeJobsList = ryeJobDao.getAllRyeJobs().first()

        // Ensure rye jobs list is sorted by name
        Assert.assertThat(ryeJobsList[0], Matchers.equalTo(ryeJobA))
        Assert.assertThat(ryeJobsList[1], Matchers.equalTo(ryeJobB))
    }

    @Test
    fun testGetRyeJob() = runBlocking {
        Assert.assertThat(ryeJobDao.getRyeJob(ryeJobA.id).first(), Matchers.equalTo(ryeJobA))
    }
}