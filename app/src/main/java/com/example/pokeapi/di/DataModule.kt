package com.example.pokeapi.di

import com.example.pokeapi.data.repositories.PokeApiCachedRepositoryImpl
import com.example.pokeapi.data.repositories.PokeApiLocalRepositoryImpl
import com.example.pokeapi.domain.repositories.PokeApiCachedRepository
import com.example.pokeapi.domain.repositories.PokeApiLocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module for providing project repositories.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun providePokeCachedRemoteRepository(pokeApiCachedRepositoryImpl: PokeApiCachedRepositoryImpl): PokeApiCachedRepository

    @Binds
    abstract fun providePokeApiLocalRepository(pokeApiLocalRepositoryImpl: PokeApiLocalRepositoryImpl): PokeApiLocalRepository

}