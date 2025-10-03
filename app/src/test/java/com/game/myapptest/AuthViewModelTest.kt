package com.game.myapptest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.game.myapptest.models.UserRequest
import com.game.myapptest.repository.UserRepository
import com.game.myapptest.ui.login.AuthViewModel
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.junit.Assert.*

@RunWith(RobolectricTestRunner::class)
class AuthViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // mock repository (we only verify interactions)
    private val userRepository = mockk<UserRepository>(relaxed = true)
    private lateinit var viewModel: AuthViewModel

    @Before
    fun setup() {
        viewModel = AuthViewModel(userRepository)
    }

    @Test
    fun `validateCredentials empty fields returns error`() {
        val (ok, msg) = viewModel.validateCredentials("", "", "", false)
        assertFalse(ok)
        assertEquals("Please provide the credentials", msg)
    }
    @Test
    fun `validateCredentials invalid email returns error`() {
        val (ok, msg) = viewModel.validateCredentials("not-an-email", "user", "password", false)
        assertFalse(ok)
        assertEquals("Email is invalid", msg)
    }

    @Test
    fun `validateCredentials password too short returns error`() {
        val (ok,msg) = viewModel.validateCredentials("king@gmail.com", "user", "pass", false)
        assertFalse(ok)
        assertEquals("Password length should be greater than 5",msg)
    }

    @Test
    fun `registerUser calls repository`() = runTest {
        val request = UserRequest("test@example.com", "password", "username")
        viewModel.registerUser(request)
        // verify that viewModel launched coroutine and called repository.registerUser
        coVerify { userRepository.registerUser(request) }
    }

}