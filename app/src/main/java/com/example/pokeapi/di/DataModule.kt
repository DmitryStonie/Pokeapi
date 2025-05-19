package com.example.pokeapi.di

import com.example.pokeapi.data.repositories.PokeApiLocalRepositoryImpl
import com.example.pokeapi.data.repositories.PokeApiRemoteRepositoryImpl
import com.example.pokeapi.domain.repositories.PokeApiLocalRepository
import com.example.pokeapi.domain.repositories.PokeApiRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun providePokeApiRemoteRepository(pokeApiRemoteRepositoryImpl: PokeApiRemoteRepositoryImpl): PokeApiRemoteRepository

    @Binds
    abstract fun providePokeApiLocalRepository(pokeApiLocalRepositoryImpl: PokeApiLocalRepositoryImpl): PokeApiLocalRepository

}