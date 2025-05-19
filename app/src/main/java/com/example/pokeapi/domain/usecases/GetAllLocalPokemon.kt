package com.example.pokeapi.domain.usecases

import com.example.pokeapi.domain.entities.Pokemon
import com.example.pokeapi.domain.repositories.PokeApiLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Use case for load list of cached pokemon from local database.
 */
class GetAllLocalPokemon @Inject constructor(val repository: PokeApiLocalRepository) {
    suspend fun invoke(): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            repository.getAllPokemon()
        }
    }
}