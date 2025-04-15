package com.example.vuapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.vuapp.data.api.AuthResponse
import com.example.vuapp.data.repository.AuthRepository
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
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel
    private lateinit var repository: AuthRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success sets keypass`() = runTest {
        val response = AuthResponse(keypass = "sports")
        coEvery {
            repository.login("/sydney/auth", "Eliza", "s12345")
        } returns Response.success(response)

        viewModel.login("Eliza", "s12345", "sydney")
        advanceUntilIdle()

        Assert.assertEquals("sports", viewModel.loginResult.getOrAwaitValue())
    }

    @Test
    fun `login failure sets error message`() = runTest {
        val errorResponse = Response.error<AuthResponse>(401, ResponseBody.create(null, ""))

        coEvery {
            repository.login("/sydney/auth", "Eliza", "wrongpass")
        } returns errorResponse

        viewModel.login("Eliza", "wrongpass", "sydney")
        advanceUntilIdle()

        Assert.assertEquals("Login failed: 401", viewModel.errorMessage.getOrAwaitValue())
    }
}
