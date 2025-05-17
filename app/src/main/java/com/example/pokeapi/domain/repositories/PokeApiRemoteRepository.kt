package com.example.pokeapi.domain.repositories

import com.example.pokeapi.domain.entities.Pokemon

interface PokeApiRemoteRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): List<Pokemon>?
}