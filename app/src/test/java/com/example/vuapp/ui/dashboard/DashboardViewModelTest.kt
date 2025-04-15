package com.example.vuapp.ui.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.vuapp.data.api.DashboardResponse
import com.example.vuapp.data.repository.DashboardRepository
import com.example.vuapp.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.ResponseBody
import org.junit.*
import retrofit2.Response

@ExperimentalCoroutinesApi
class DashboardViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DashboardViewModel
    private lateinit var repository: DashboardRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = DashboardViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadDashboard success sets entities`() = runTest {
        val dummyEntities = listOf(mapOf("property1" to "Football"))
        val response = DashboardResponse(entities = dummyEntities)

        coEvery { repository.getDashboardData("sports") } returns Response.success(response)

        viewModel.loadDashboard("sports")
        advanceUntilIdle()

        Assert.assertEquals(dummyEntities, viewModel.entities.getOrAwaitValue())
    }

    @Test
    fun `loadDashboard failure sets error`() = runTest {
        val errorResponse = Response.error<DashboardResponse>(404, ResponseBody.create(null, ""))

        coEvery { repository.getDashboardData("invalid") } returns errorResponse

        viewModel.loadDashboard("invalid")
        advanceUntilIdle()

        Assert.assertEquals("Error: 404", viewModel.error.getOrAwaitValue())
    }
}
