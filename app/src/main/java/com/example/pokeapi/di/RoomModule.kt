package com.example.pokeapi.di

import android.content.Context
import androidx.room.Room
import com.example.pokeapi.data.datasources.pokeapilocal.PokemonDao
import com.example.pokeapi.data.datasources.pokeapilocal.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @DatabaseName
    fun provideDatabaseName(): String = "database"

    @Provides
    @Singleton
    fun provideDatabase(
        @DatabaseName databaseName: String, @ApplicationContext context: Context
    ): PokemonDatabase = Room.databaseBuilder(
        context,
        PokemonDatabase::class.java,
        databaseName,
    ).build()

    @Provides
    fun providePokemonDao(database: PokemonDatabase): PokemonDao = database.pokemonDao()

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DatabaseName