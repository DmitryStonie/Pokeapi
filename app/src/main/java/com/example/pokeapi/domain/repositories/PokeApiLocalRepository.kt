package com.example.pokeapi.domain.repositories

import com.example.pokeapi.domain.entities.Pokemon

interface PokeApiLocalRepository {
    suspend fun getPokemon(id: Int): Pokemon?
    suspend fun getAllPokemon(): List<Pokemon>
}