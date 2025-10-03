package com.game.myapptest.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.myapptest.models.RoomUser
import com.game.myapptest.repository.RoomNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RoomAuthViewModel @Inject constructor(private val repo: RoomNoteRepository) : ViewModel() {
    fun signup(username: String, email: String, password: String, onSuccess: (RoomUser) -> Unit) {
        viewModelScope.launch {
            val id = repo.signup(RoomUser(username = username, email = email, password = password))
            onSuccess(RoomUser(id.toInt(), username, email, password))
        }
    }

    fun signin(email: String, password: String, onResult: (RoomUser?) -> Unit) {
        viewModelScope.launch {
            val user = repo.signin(email, password)
            onResult(user)
        }
    }
}