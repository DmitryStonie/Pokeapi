package com.example.pokeapi.domain.usecases

import com.example.pokeapi.domain.entities.Pokemon
import com.example.pokeapi.domain.repositories.PokeApiLocalRepository
import com.example.pokeapi.domain.repositories.PokeApiRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLocalPokemon @Inject constructor(val repository: PokeApiLocalRepository) {
    suspend fun invoke(id: Int): Pokemon?{
        return withContext(Dispatchers.IO){
            repository.getPokemon(id)
        }
    }
}