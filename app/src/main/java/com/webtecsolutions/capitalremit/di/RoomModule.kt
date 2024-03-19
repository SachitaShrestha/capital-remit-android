package com.webtecsolutions.capitalremit.di

import android.content.Context
import androidx.room.Room
import com.webtecsolutions.capitalremit.data.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun getRoomDatabase(@ApplicationContext context: Context): LocalDatabase =
        Room.databaseBuilder(context, LocalDatabase::class.java, "local_database")
            .fallbackToDestructiveMigration().allowMainThreadQueries().build()


    @Singleton
    @Provides
    fun getTransactionDao(localDatabase: LocalDatabase) = localDatabase.getTransactionDao()

}