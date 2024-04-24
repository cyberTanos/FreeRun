package com.example.maps.di

import com.example.maps.domain.MainRepository
import com.example.maps.domain.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindRepo(impl: MainRepositoryImpl): MainRepository
}
