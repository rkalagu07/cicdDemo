package com.game.myapptest.di

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.game.myapptest.models.RoomNote
import com.game.myapptest.models.RoomUser

@Dao
interface UserDao {
    @Insert suspend fun insertUser(user: RoomUser): Long
    @Query("SELECT * FROM users WHERE email = :email") suspend fun getUserByEmail(email: String): RoomUser?
}

@Dao
interface NoteDao {
    @Insert suspend fun insertNote(note: RoomNote): Long
    @Query("UPDATE notes SET title = :title, description = :description WHERE id = :id")
    suspend fun updateNote(id: Int, title: String, description: String)
    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNote(id: Int)
    @Query("SELECT * FROM notes WHERE userId = :userId") suspend fun getNotesByUser(userId: Int): List<RoomNote>
}
