package com.example.pokeapi.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.pokeapi.data.datasources.mappers.toPokemon
import com.example.pokeapi.data.datasources.pagingsource.ExamplePagingSource
import com.example.pokeapi.data.datasources.pagingsource.ExampleRemoteMediator
import com.example.pokeapi.data.datasources.pokeapilocal.PokemonDatabase
import com.example.pokeapi.data.datasources.pokeapiremote.PokeApiDatasource
import com.example.pokeapi.data.repositories.PokeApiRemoteRepositoryImpl
import com.example.pokeapi.domain.entities.Pokemon
import com.example.pokeapi.domain.usecases.GetPokemonListRemote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val database: PokemonDatabase,
    private val pokeApiDatasource: PokeApiDatasource,
    val pokeApiRemoteRepositoryImpl: PokeApiRemoteRepositoryImpl
) : ViewModel() {
    val pokemonState = MutableLiveData<List<Pokemon>>()
    val selectedPokemon = MutableLiveData<Pokemon?>()
    val pokemonDao = database.pokemonDao()


    @OptIn(ExperimentalPagingApi::class)
    val flow = Pager(
        config = PagingConfig(pageSize = 30, initialLoadSize = 30),
        remoteMediator = ExampleRemoteMediator(database, pokeApiDatasource),
        pagingSourceFactory = {
            pokemonDao.pagingSource()
        }
    ).flow
        .map { pagingData -> pagingData.map { it.toPokemon() } }


    fun getPokemonList() {
//            getPokemonListRemote.invoke()?.let {
//                Log.d("INFO", "got ${it}")
//                pokemonState.postValue(it)
//            }
        Log.d("INFO", "got ${flow}")
    }

    fun selectPokemon(index: Int) {
        selectedPokemon.value = pokemonState.value?.get(index)
    }

    fun resetSelectedPokemon() {
        selectedPokemon.value = null
    }
}