package com.game.myapptest

import com.game.myapptest.api.UserAPI
import com.game.myapptest.repository.UserRepository
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import io.mockk.mockk
import org.robolectric.RobolectricTestRunner
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.game.myapptest.models.User
import com.game.myapptest.models.UserRequest
import com.game.myapptest.models.UserResponse
import com.game.myapptest.utils.NetworkResult
import io.mockk.coEvery
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
class UserRepositoryTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val userAPI = mockk<UserAPI>()
    private lateinit var repository: UserRepository

    @Before
    fun setup() {
        repository = UserRepository(userAPI)
    }
    @Test
    fun `registerUser - success posts Success` () = runTest {
        val req = UserRequest("king@gmail.com","pwd@123","king")
        val dummyUser = mockk<User>()
        val userResp = UserResponse("sometoken",dummyUser)
        val response = Response.success(userResp)
        coEvery { userAPI.signup(req) } returns response
        repository.registerUser(req)
        // get the LiveData value (helper provided below)
        val emitted = repository.userResponseLiveData.getOrAwaitValue()
        assertTrue(emitted is NetworkResult.Success)
        assertEquals("sometoken", (emitted as NetworkResult.Success).data!!.token)
    }
}