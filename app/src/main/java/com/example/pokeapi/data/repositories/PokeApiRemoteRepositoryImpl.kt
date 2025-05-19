package com.example.pokeapi.data.repositories

import com.example.pokeapi.data.datasources.pokeapiremote.PokeApiDatasource
import com.example.pokeapi.domain.entities.Pokemon
import com.example.pokeapi.domain.repositories.PokeApiRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.pokeapi.data.datasources.mappers.toPokemon

class PokeApiRemoteRepositoryImpl @Inject constructor(private val pokeApiDatasource: PokeApiDatasource) :
    PokeApiRemoteRepository {
    override suspend fun getPokemonList(
        limit: Int, offset: Int
    ): List<Pokemon>? {
        val pokemonInfo = pokeApiDatasource.getPokemonInfoList(limit, offset)
        return pokemonInfo?.map{ pokemonDto -> pokemonDto.pokemonInfo.toPokemon()}
    }

}