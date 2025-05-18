package com.example.pokeapi.data.repositories

import com.example.pokeapi.data.datasources.pokeapiremote.PokeApiDatasource
import com.example.pokeapi.domain.entities.Pokemon
import com.example.pokeapi.domain.repositories.PokeApiRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokeApiRemoteRepositoryImpl @Inject constructor(private val pokeApiDatasource: PokeApiDatasource) :
    PokeApiRemoteRepository {
    override suspend fun getPokemonList(
        limit: Int, offset: Int
    ): List<Pokemon>? {
        val pokemonListResponse = pokeApiDatasource.getPokemonsList(limit, offset)
        if (pokemonListResponse.isSuccessful) {
            val names = pokemonListResponse.body()?.results?.map { it.name }
            if (names != null) {
                val pokemons = ArrayList<Pokemon>()
                withContext(Dispatchers.IO) {
                    for (name in names) {
                        launch {
                            val pokemon = getPokemon(name)
                            pokemon?.let { pokemons.add(it) }
                        }
                    }
                }
                return pokemons
            } else {
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
//                val res = pokeApiDatasource.downloadPokemonImage(imageUrl)
                var hp = 0
                var attack = 0
                var defense = 0
                var specialAttack = 0
                var specialDefense = 0
                var speed = 0
                for (stat in response.body()!!.stats) {
                    when (stat.stat.name) {
                        "hp" -> hp = stat.baseStat
                        "attack" -> attack = stat.baseStat
                        "defense" -> defense = stat.baseStat
                        "special-attack" -> specialAttack = stat.baseStat
                        "special-defense" -> specialDefense = stat.baseStat
                        "speed" -> speed = stat.baseStat
                        else -> {}
                    }
                }
                return Pokemon(
                    response.body()!!.id,
                    response.body()!!.name,
                    response.body()!!.height,
                    response.body()!!.weight,
                    response.body()!!.types.map { type -> type.type.name },
                    hp,
                    attack,
                    defense,
                    specialAttack,
                    specialDefense,
                    speed,
                    imageUrl
                )
            } else {
                return null
            }
        } else {
            return null
        }
    }

}