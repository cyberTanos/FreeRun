package com.example.maps.di

import android.content.Context
import com.example.maps.other.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {

    @Singleton
    @Provides
    fun provideSharedPref(@ApplicationContext appContext: Context): com.example.maps.data.preference.Preferences {
        val sharedPref = appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return com.example.maps.data.preference.Preferences(sharedPref)
    }
}
