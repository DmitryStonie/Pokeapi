package com.example.pokeapi.di

import com.example.pokeapi.data.datasources.pokeapiremote.PokeApiDatasource
import com.example.pokeapi.data.datasources.pokeapiremote.PokeApiService
import com.example.pokeapi.data.repositories.PokeApiRemoteRepositoryImpl
import com.example.pokeapi.domain.repositories.PokeApiRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun providePokeApiRemoteRepository(pokeApiRemoteRepositoryImpl: PokeApiRemoteRepositoryImpl): PokeApiRemoteRepository


}