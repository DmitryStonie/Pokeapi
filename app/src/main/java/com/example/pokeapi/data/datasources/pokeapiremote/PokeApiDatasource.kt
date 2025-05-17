package com.example.pokeapi.data.datasources.pokeapiremote

import com.example.pokeapi.data.datasources.pokeapiremote.data.PokemonInfo
import com.example.pokeapi.data.datasources.pokeapiremote.data.PokemonList
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class PokeApiDatasource @Inject constructor(val pokeApiService: PokeApiService) {
    suspend fun getPokemonsList(
        limit: Int,
        offset: Int
    ): Response<PokemonList> {
        return pokeApiService.getPokemonsList(limit, offset)
    }

    suspend fun getPokemon(name: String): Response<PokemonInfo> {
        return  pokeApiService.getPokemon(name)
    }

    suspend fun downloadPokemonImage(imageUrl: String): ResponseBody {
        return pokeApiService.downloadPokemonImage(imageUrl)
    }


}