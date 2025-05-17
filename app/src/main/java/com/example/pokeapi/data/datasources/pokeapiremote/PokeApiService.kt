package com.example.pokeapi.data.datasources.pokeapiremote

import com.example.pokeapi.data.datasources.pokeapiremote.data.PokemonInfo
import com.example.pokeapi.data.datasources.pokeapiremote.data.PokemonList
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonsList(@Query("limit") limit: Int, @Query("offset") offset: Int): Response<PokemonList>

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): Response<PokemonInfo>

    @Streaming
    @GET
    suspend fun downloadPokemonImage(@Url imageUrl: String): ResponseBody
}