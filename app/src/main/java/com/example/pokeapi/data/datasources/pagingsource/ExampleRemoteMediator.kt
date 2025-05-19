package com.example.pokeapi.data.datasources.pagingsource

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.pokeapi.data.datasources.mappers.toPokemon
import com.example.pokeapi.data.datasources.mappers.toPokemonEntity
import com.example.pokeapi.data.datasources.pokeapilocal.PokemonDao
import com.example.pokeapi.data.datasources.pokeapilocal.PokemonDatabase
import com.example.pokeapi.data.datasources.pokeapilocal.PokemonEntity
import com.example.pokeapi.data.datasources.pokeapiremote.PokeApiDatasource
import com.example.pokeapi.data.datasources.pokeapiremote.PokeApiService
import com.example.pokeapi.data.repositories.PokeApiRemoteRepositoryImpl
import com.example.pokeapi.domain.entities.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.max
import kotlin.random.Random


@OptIn(ExperimentalPagingApi::class)
class ExampleRemoteMediator @Inject constructor(
    private val database: PokemonDatabase,
    private val networkService: PokeApiDatasource,
    val startIndex: Int? = null
) : RemoteMediator<Int, PokemonEntity>() {
    private val pokemonDao = database.pokemonDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val loadKey = when (loadType) {
                    LoadType.REFRESH -> {
                        Log.d("INFO", "REFRESH")
                        startIndex ?: (pokemonDao.getMinimalPokemonOrder()?.minus(1) ?: 0)
                    }

                    LoadType.PREPEND -> {
                        Log.d("INFO", "PREPEND")
                        var order = 1
                        database.withTransaction {
                            order = pokemonDao.getMinimalPokemonOrder() ?: 1
                        }
                        if (order == 1) {
                            Log.d("INFO", "PREPEND end")
                            return@withContext MediatorResult.Success(endOfPaginationReached = true)
                        } else {
                            max(order - 1 - state.config.pageSize, 0)
                        }
                    }

                    LoadType.APPEND -> {
                        Log.d("INFO", "APPEND")
                        var lastItem = state.lastItemOrNull()
                        if (lastItem == null) {
                            val lastInd = pokemonDao.getMaximalPokemonOrder() ?: 0
                            if (lastInd < 1302) {
                                lastInd
                            } else{
                                return@withContext MediatorResult.Success(endOfPaginationReached = true)
                            }
                        } else {
                            lastItem.order
                        }
                    }
                }
                Log.d("INFO", "Load with els ${state.config.pageSize} and ${loadKey}")
                val response = networkService.getPokemonInfoList(state.config.pageSize, loadKey)
                Log.d("INFO", "end loading")
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        pokemonDao.clearAll()
                    }
                    if (response != null) {
                        pokemonDao.insertAll(response.map { pokemonDto -> pokemonDto.toPokemonEntity() })
                        Log.d("INFO", "elements added")
                    }
                }
                Log.d("INFO", "End result is ${response?.isEmpty() ?: true}")
                MediatorResult.Success(
                    endOfPaginationReached = response?.isEmpty() ?: true
                )
            } catch (e: IOException) {
                MediatorResult.Error(e)
            } catch (e: HttpException) {
                MediatorResult.Error(e)
            }
        }
    }
}