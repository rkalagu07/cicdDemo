package com.game.myapptest.di

import android.content.Context
import androidx.room.Room
import com.game.myapptest.repository.RoomNoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "notes_db").build()

    @Provides fun provideUserDao(db: AppDatabase) = db.userDao()
    @Provides fun provideNoteDao(db: AppDatabase) = db.noteDao()

    @Provides
    @Singleton
    fun provideRepository(userDao: UserDao, noteDao: NoteDao): RoomNoteRepository {
        return RoomNoteRepository(userDao, noteDao)
    }
}