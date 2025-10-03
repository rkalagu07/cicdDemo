package com.game.myapptest.repository

import com.game.myapptest.di.NoteDao
import com.game.myapptest.di.UserDao
import com.game.myapptest.models.RoomNote
import com.game.myapptest.models.RoomUser
import jakarta.inject.Inject
import jakarta.inject.Singleton


@Singleton
class RoomNoteRepository @Inject constructor(
    private val userDao: UserDao,
    private val noteDao: NoteDao
) {
    suspend fun signup(user: RoomUser): Long = userDao.insertUser(user)
    suspend fun signin(email: String, password: String): RoomUser? {
        val user = userDao.getUserByEmail(email)
        return if (user != null && user.password == password) user else null
    }

    suspend fun createNote(note: RoomNote) = noteDao.insertNote(note)
    suspend fun updateNote(id: Int, title: String, description: String) = noteDao.updateNote(id, title, description)
    suspend fun deleteNote(id:Int) = noteDao.deleteNote(id)
    suspend fun getNotes(userId: Int) = noteDao.getNotesByUser(userId)
}
