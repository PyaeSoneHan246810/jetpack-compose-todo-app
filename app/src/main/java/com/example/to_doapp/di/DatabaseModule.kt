package com.example.to_doapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.to_doapp.data.ToDoDatabase
import com.example.to_doapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideToDoDatabase(
        @ApplicationContext context: Context
    ) = Room
        .databaseBuilder(context, ToDoDatabase::class.java, Constants.TODO_DATABASE_NAME)
        .build()

    @Singleton
    @Provides
    fun provideToDoDao(
        database: ToDoDatabase
    ) = database.ToDoDao()
}