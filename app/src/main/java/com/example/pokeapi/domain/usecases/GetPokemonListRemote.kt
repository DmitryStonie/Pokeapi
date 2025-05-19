package com.example.pokeapi.domain.usecases

import com.example.pokeapi.domain.entities.Pokemon
import com.example.pokeapi.domain.repositories.PokeApiRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPokemonListRemote @Inject constructor(val repository: PokeApiRemoteRepository) {
    suspend fun invoke(): List<Pokemon>?{
        return withContext(Dispatchers.IO){
            repository.getPokemonList(0, 30)
        }
    }
}