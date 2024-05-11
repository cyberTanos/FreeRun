package com.example.maps.di

import android.content.Context
import com.example.maps.other.ResourceProvider
import com.example.maps.other.ResourceProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ResourceModule {

    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProviderImpl(context)
    }
}
