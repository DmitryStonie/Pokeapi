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


@OptIn(ExperimentalPagingApi::class)
class ExampleRemoteMediator @Inject constructor(
    private val database: PokemonDatabase,
    private val networkService: PokeApiDatasource
) : RemoteMediator<Int, PokemonEntity>() {
    val pokemonDao = database.pokemonDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val loadKey = when (loadType) {
                    LoadType.REFRESH -> {
                        Log.d("INFO", "REFRESH")
                        0
                    }

                    LoadType.PREPEND -> {
                        Log.d("INFO", "PREPEND")
                        return@withContext MediatorResult.Success(endOfPaginationReached = true)
                    }

                    LoadType.APPEND -> {
                        Log.d("INFO", "APPEND")
                        val lastItem = state.lastItemOrNull()
                        if (lastItem == null) {
                            Log.d("INFO", "last item is null")
                            return@withContext MediatorResult.Success(endOfPaginationReached = false)
                        } else {
                            lastItem.id
                        }
                    }
                }
                val response = networkService.getPokemonInfoList(state.config.pageSize, loadKey)
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        pokemonDao.clearAll()
                    }
                    if (response != null) {
                        Log.d("INFO", "${response.size}")
                        pokemonDao.insertAll(response.map { pokemonInfo -> pokemonInfo.toPokemonEntity() })
                    }
                }
                Log.d("INFO", "Is end ${response?.isEmpty() ?: true}")
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