package com.example.pokeapi.data.datasources.pokeapiremote

import com.example.pokeapi.data.datasources.pokeapiremote.data.PokemonDto
import com.example.pokeapi.data.datasources.pokeapiremote.data.PokemonInfo
import com.example.pokeapi.data.datasources.pokeapiremote.data.PokemonList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

/**
 * Retrofit class for loading pokemon from internet.
 */
class PokeApiDatasource @Inject constructor(val pokeApiService: PokeApiService) {
    private suspend fun getPokemonList(
        limit: Int, offset: Int
    ): Response<PokemonList> {
        return pokeApiService.getPokemonList(limit, offset)
    }
    /**
     * Function loads limit pokemon from server starting with offset pokemon
     * @param limit number of pokemon to load
     * @param offset position of first pokemon lo load
     */
    suspend fun getPokemonInfoList(
        limit: Int, offset: Int
    ): List<PokemonDto>? {
        val pokemonListResponse = getPokemonList(limit, offset)
        if (pokemonListResponse.isSuccessful) {
            val names = pokemonListResponse.body()?.results?.map { it.name }
            if (names != null) {
                val mutex = Mutex()
                val pokemon = ArrayList<PokemonDto>()
                withContext(Dispatchers.IO) {
                    for (i in names.indices) {
                        launch {
                            val pokemonInfo = getPokemon(names[i])
                            mutex.withLock {
                                pokemonInfo?.let { pokemonInfo ->
                                    pokemon.add(PokemonDto(pokemonInfo, i + 1 + offset))
                                }
                            }
                        }
                    }
                }
                return pokemon
            } else {
                return null
            }
        } else {
            return null
        }
    }

    /**
     * Function loads pokemon full information by it's name. Function fails if it couldn't load 3 times in a row.
     * @param name name of pokemon to load
     */
    suspend fun getPokemon(name: String): PokemonInfo? {
        repeat(NUM_OF_ATTEMPTS) {
            val response = pokeApiService.getPokemon(name)
            if (response.isSuccessful) {
                return response.body()
            }
        }
        return null
    }

    companion object {
        const val NUM_OF_ATTEMPTS = 3
    }

}