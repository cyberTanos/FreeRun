package com.example.maps.di

import android.content.Context
import androidx.room.Room
import com.example.maps.data.local.RunDAO
import com.example.maps.data.local.RunDatabase
import com.example.maps.other.Constants.RUN_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Singleton
    @Provides
    fun provideRunDatabase(@ApplicationContext appContext: Context): RunDAO {
        val db = Room.databaseBuilder(
            appContext,
            RunDatabase::class.java,
            RUN_DATABASE_NAME
        ).build()
        return db.getRunDao()
    }
}
