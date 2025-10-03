package com.game.myapptest.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.game.myapptest.models.RoomNote
import com.game.myapptest.models.RoomUser


@Database(entities = [RoomUser::class, RoomNote::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun noteDao(): NoteDao
}