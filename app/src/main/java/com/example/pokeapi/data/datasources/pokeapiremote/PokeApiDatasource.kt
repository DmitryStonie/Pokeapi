package com.example.pokeapi.data.datasources.pokeapiremote

import android.util.Log
import com.example.pokeapi.data.datasources.pokeapiremote.data.PokemonInfo
import com.example.pokeapi.data.datasources.pokeapiremote.data.PokemonList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class PokeApiDatasource @Inject constructor(val pokeApiService: PokeApiService) {
    private suspend fun getPokemonList(
        limit: Int,
        offset: Int
    ): Response<PokemonList> {
        return pokeApiService.getPokemonList(limit, offset)
    }

    suspend fun getPokemonInfoList(
        limit: Int,
        offset: Int
    ): List<PokemonInfo>? {
        val pokemonListResponse = getPokemonList(limit, offset)
        if (pokemonListResponse.isSuccessful) {
            val names = pokemonListResponse.body()?.results?.map { it.name }
            if (names != null) {
                val mutex = Mutex()
                val pokemon = ArrayList<PokemonInfo>()
                runBlocking(Dispatchers.IO) {
                    for (name in names) {
                        launch {
                            val pokemonInfo = getPokemon(name)
                            mutex.withLock {
                                pokemonInfo?.let {
                                    pokemon.add(it)
                                }
                            }
                        }
                    }
                }
                Log.d("INFO", "in datasource got ${pokemon.size}")
                return pokemon
            } else {
                return null
            }
        } else {
            return null
        }
    }

    suspend fun getPokemon(name: String): PokemonInfo? {
        for (i in 1..3) {
            val response = pokeApiService.getPokemon(name)
            if (response.isSuccessful) {
                return response.body()
            }
            Log.d("ERROR", "Pokemon response has failed")
        }
        return null
    }

    suspend fun downloadPokemonImage(imageUrl: String): ResponseBody {
        return pokeApiService.downloadPokemonImage(imageUrl)
    }


}