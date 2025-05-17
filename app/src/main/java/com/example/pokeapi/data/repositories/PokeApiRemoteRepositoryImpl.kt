package com.example.pokeapi.data.repositories

import com.example.pokeapi.data.datasources.pokeapiremote.PokeApiDatasource
import com.example.pokeapi.domain.entities.Pokemon
import com.example.pokeapi.domain.entities.PokemonStat
import com.example.pokeapi.domain.repositories.PokeApiRemoteRepository
import javax.inject.Inject

class PokeApiRemoteRepositoryImpl @Inject constructor(private val pokeApiDatasource: PokeApiDatasource) :
    PokeApiRemoteRepository {
    override suspend fun getPokemonList(
        limit: Int,
        offset: Int
    ): List<Pokemon>? {
        val pokemonListResponse = pokeApiDatasource.getPokemonsList(limit, offset)
        if (pokemonListResponse.isSuccessful) {
            val names = pokemonListResponse.body()?.results?.map { it.name }
            if (names != null) {
                val pokemons = ArrayList<Pokemon>()
                for (name in names) {
                    val pokemon = getPokemon(name)
                    pokemon?.let { pokemons.add(it) }
                }
                return pokemons
            } else{
                return null
            }
        } else {
            return null
        }
    }

    private suspend fun getPokemon(name: String): Pokemon? {
        val response = pokeApiDatasource.getPokemon(name)
        if (response.isSuccessful) {
            val imageUrl = response.body()?.sprites?.frontDefault
            if (imageUrl != null) {
                val res = pokeApiDatasource.downloadPokemonImage(imageUrl)
                return Pokemon(
                    response.body()!!.id,
                    response.body()!!.name,
                    response.body()!!.height,
                    response.body()!!.weight,
                    response.body()!!.types.map { type -> type.type.name },
                    response.body()!!.stats.map { stat ->
                        PokemonStat(
                            stat.stat.name,
                            stat.baseStat,
                            stat.effort
                        )
                    },
                    res.toString()
                )
            } else {
                return null
            }
        } else {
            return null
        }
    }

}