package com.example.pokeapi.di

import com.example.pokeapi.data.datasources.pokeapiremote.PokeApiDatasource
import com.example.pokeapi.data.datasources.pokeapiremote.PokeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("API_BASE_URL")
    fun providesBaseUrl() : String = "https://pokeapi.co/api/v2/"

    @Provides
    @Singleton
    fun provideRetrofit(@Named("API_BASE_URL") BASE_URL : String) : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun providePokeApiService(retrofit : Retrofit) : PokeApiService = retrofit.create(PokeApiService::class.java)

    @Provides
    @Singleton
    fun providePokeApiDatasource(pokeApiService : PokeApiService) : PokeApiDatasource =
        PokeApiDatasource(pokeApiService)
}