package com.example.kotlinpractice.roomtodolist.dagger.injection.module

import com.example.kotlinpractice.roomtodolist.persistance.Repository
import com.example.kotlinpractice.roomtodolist.persistance.ToDoDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideRepository(db: ToDoDatabase): Repository {
        return Repository(db)
    }
}
