package com.application.shoprye.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.application.shoprye.MainCoroutineRule
import com.application.shoprye.data.RyeJobDatabase
import com.application.shoprye.repositories.RyeJobRepository
import com.application.shoprye.repositories.ShoppingCartRepository
import com.application.shoprye.runBlockingTest
import com.application.shoprye.utilities.getValue
import com.application.shoprye.utilities.testRyeJob
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.*
import org.junit.rules.RuleChain
import javax.inject.Inject
import kotlin.jvm.Throws

@HiltAndroidTest
class RyeJobDetailViewModelTest {

    private lateinit var appDatabase: RyeJobDatabase
    private lateinit var viewModel: RyeJobDetailViewModel
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val coroutineRule = MainCoroutineRule()

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)
        .around(coroutineRule)

    @Inject
    lateinit var ryeJobRepository: RyeJobRepository

    @Inject
    lateinit var shoppingCartRepository: ShoppingCartRepository

    @Before
    fun setUp() {
        hiltRule.inject()

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, RyeJobDatabase::class.java).build()

        val savedStateHandle: SavedStateHandle = SavedStateHandle().apply {
            set(RYE_JOB_ID_SAVED_STATE_KEY, testRyeJob.id.toString())
        }
        viewModel = RyeJobDetailViewModel(savedStateHandle, ryeJobRepository, shoppingCartRepository)
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    @Test
    @Throws(InterruptedException::class)
    fun testDefaultValues() = coroutineRule.runBlockingTest {
        Assert.assertFalse(getValue(viewModel.isInCart))
    }

    companion object {
        private const val RYE_JOB_ID_SAVED_STATE_KEY = "ryeJobId"
    }
}
