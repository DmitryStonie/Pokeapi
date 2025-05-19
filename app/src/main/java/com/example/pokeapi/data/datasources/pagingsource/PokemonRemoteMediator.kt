package com.example.pokeapi.data.datasources.pagingsource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.pokeapi.data.mappers.toPokemonEntity
import com.example.pokeapi.data.datasources.pokeapilocal.PokemonDatabase
import com.example.pokeapi.data.datasources.pokeapilocal.PokemonEntity
import com.example.pokeapi.data.datasources.pokeapiremote.PokeApiDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.max


@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator @Inject constructor(
    private val database: PokemonDatabase,
    private val networkService: PokeApiDatasource,
    val startIndex: Int? = null
) : RemoteMediator<Int, PokemonEntity>() {
    private val pokemonDao = database.pokemonDao()

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val loadKey = when (loadType) {
                    LoadType.REFRESH -> {
                        startIndex ?: (pokemonDao.getMinimalPokemonPosition()?.minus(1) ?: 0)
                    }

                    LoadType.PREPEND -> {
                        var minPos = MIN_POKEMON_POS
                        database.withTransaction {
                            minPos = pokemonDao.getMinimalPokemonPosition() ?: 1
                        }
                        if (minPos == MIN_POKEMON_POS) {
                            return@withContext MediatorResult.Success(endOfPaginationReached = true)
                        } else {
                            max(minPos - state.config.pageSize, MIN_POKEMON_POS)
                        }
                    }

                    LoadType.APPEND -> {
                        var lastItem = state.lastItemOrNull()
                        if (lastItem == null) {
                            val lastInd = pokemonDao.getMaximalPokemonPosition() ?: MIN_POKEMON_POS
                            if (lastInd < MAX_POKEMON_POS) {
                                lastInd
                            } else {
                                return@withContext MediatorResult.Success(endOfPaginationReached = true)
                            }
                        } else {
                            lastItem.position
                        }
                    }
                }
                val response = networkService.getPokemonInfoList(state.config.pageSize, loadKey)
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        pokemonDao.clearAll()
                    }
                    if (response != null) {
                        pokemonDao.insertAll(response.map { pokemonDto -> pokemonDto.toPokemonEntity() })
                    }
                }
                MediatorResult.Success(
                    endOfPaginationReached = response?.isEmpty() != false
                )
            } catch (e: IOException) {
                MediatorResult.Error(e)
            } catch (e: HttpException) {
                MediatorResult.Error(e)
            }
        }
    }

    companion object {
        const val MIN_POKEMON_POS = 0
        const val MAX_POKEMON_POS = 1301
    }
}