package com.example.pokeapi.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.pokeapi.data.mappers.toPokemon
import com.example.pokeapi.data.datasources.pagingsource.PokemonRemoteMediator
import com.example.pokeapi.data.datasources.pokeapilocal.PokemonDatabase
import com.example.pokeapi.data.datasources.pokeapiremote.PokeApiDatasource
import com.example.pokeapi.domain.entities.Pokemon
import com.example.pokeapi.domain.repositories.PokeApiCachedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokeApiCachedRepositoryImpl @Inject constructor(
    private val database: PokemonDatabase,
    private val pokeApiDatasource: PokeApiDatasource,
): PokeApiCachedRepository {
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPokemonPages(initialIndex: Int?): Flow<PagingData<Pokemon>> {
        val pokemonDao = database.pokemonDao()
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = INITIAL_LOAD_SIZE, prefetchDistance = PREFETCH_DISTANCE),
            remoteMediator = PokemonRemoteMediator(database, pokeApiDatasource, initialIndex),
            pagingSourceFactory = {
                pokemonDao.pagingSource()
            }).flow.map { pagingData ->
            pagingData.map { pokemonEntity ->
                pokemonEntity.toPokemon()
            }
        }
    }
    companion object{
        const val PAGE_SIZE = 30
        const val INITIAL_LOAD_SIZE = 60
        const val PREFETCH_DISTANCE = 0
    }
}