package com.example.pokeapi.data.datasources.pokeapilocal

import javax.inject.Inject

class PokeApiLocalDatasource @Inject constructor(private val dao: PokemonDao) {

    suspend fun getPokemon(id: Int): PokemonEntity? {
        return dao.getPokemon(id)
    }
    suspend fun getAllPokemon(): List<PokemonEntity>? {
        return dao.getAll()
    }
}