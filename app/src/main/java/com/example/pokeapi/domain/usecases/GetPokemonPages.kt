package com.example.pokeapi.domain.usecases

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.pokeapi.domain.entities.Pokemon
import com.example.pokeapi.domain.repositories.PokeApiCachedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for load pages of pokemon via Paging library.
 */
class GetPokemonPages @Inject constructor(
    private val pokeApiCachedRepository: PokeApiCachedRepository
) {
    @OptIn(ExperimentalPagingApi::class)
    suspend fun invoke(initialIndex: Int?): Flow<PagingData<Pokemon>> {
        return pokeApiCachedRepository.getPokemonPages(initialIndex)
    }
}