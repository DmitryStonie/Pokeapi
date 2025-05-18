package com.example.pokeapi.di

import android.content.Context
import androidx.room.Room
import com.example.pokeapi.data.datasources.pokeapilocal.PokemonDao
import com.example.pokeapi.data.datasources.pokeapilocal.PokemonDatabase
import com.example.pokeapi.data.repositories.PokeApiRemoteRepositoryImpl
import com.example.pokeapi.domain.repositories.PokeApiRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Named("DATABASE_NAME")
    fun provideDatabaseName(): String = "database"

    @Provides
    @Singleton
    fun provideDatabase(@Named("DATABASE_NAME") databaseName: String, @ApplicationContext context: Context): PokemonDatabase =
        Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            databaseName,
        ).build()

    @Provides
    fun providePokemonDao(database: PokemonDatabase): PokemonDao = database.pokemonDao()


}