package com.example.pokeapi.domain.repositories

import androidx.paging.PagingData
import com.example.pokeapi.domain.entities.Pokemon
import kotlinx.coroutines.flow.Flow

/**
 * Repository for load remote and cached pokemon.
 */
interface PokeApiCachedRepository {
    suspend fun getPokemonPages(initialIndex: Int?): Flow<PagingData<Pokemon>>
}