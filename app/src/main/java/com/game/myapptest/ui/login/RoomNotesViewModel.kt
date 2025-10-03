package com.game.myapptest.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.myapptest.models.RoomNote
import com.game.myapptest.repository.RoomNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RoomNotesViewModel @Inject constructor(private val repo: RoomNoteRepository) : ViewModel() {
    fun addNote(title: String, desc: String, userId: Int) {
        viewModelScope.launch {
            repo.createNote(RoomNote(title = title, description = desc, userId = userId))
        }
    }

    fun loadNotes(userId: Int, onLoaded: (List<RoomNote>) -> Unit) {
        viewModelScope.launch {
            onLoaded(repo.getNotes(userId))
        }
    }
    fun updateNote(id:Int, title: String, desc: String) {
        viewModelScope.launch {
            repo.updateNote(id, title, desc)
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            repo.deleteNote(id = id)
        }
    }
}