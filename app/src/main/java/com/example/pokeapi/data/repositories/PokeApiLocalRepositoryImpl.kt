package com.example.pokeapi.data.repositories

import com.example.pokeapi.data.mappers.toPokemon
import com.example.pokeapi.data.datasources.pokeapilocal.PokeApiLocalDatasource
import com.example.pokeapi.domain.entities.Pokemon
import com.example.pokeapi.domain.repositories.PokeApiLocalRepository
import javax.inject.Inject

class PokeApiLocalRepositoryImpl @Inject constructor(private val pokeApiLocalDatasource: PokeApiLocalDatasource) :
    PokeApiLocalRepository {
    override suspend fun getPokemon(id: Int): Pokemon? {
        return pokeApiLocalDatasource.getPokemon(id)?.toPokemon()
    }

    override suspend fun getAllPokemon(): List<Pokemon> {
        return pokeApiLocalDatasource.getAllPokemon()?.map { pokemonEntity -> pokemonEntity.toPokemon() } ?: listOf()
    }
}