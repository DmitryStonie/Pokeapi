package com.example.pokeapi.presentation

import android.R
import android.renderscript.Sampler.Value
import android.util.Log
import androidx.lifecycle.LiveData
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
import com.example.pokeapi.data.datasources.pokeapilocal.PokemonEntity
import com.example.pokeapi.data.datasources.pokeapiremote.PokeApiDatasource
import com.example.pokeapi.data.datasources.pokeapiremote.data.PokemonInfo
import com.example.pokeapi.data.repositories.PokeApiRemoteRepositoryImpl
import com.example.pokeapi.domain.entities.Pokemon
import com.example.pokeapi.domain.repositories.PokeApiLocalRepository
import com.example.pokeapi.domain.usecases.GetPokemonListRemote
import com.example.pokeapi.presentation.recyclerview.PokemonItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class MainViewModel @Inject constructor(
    private val database: PokemonDatabase,
    private val pokeApiDatasource: PokeApiDatasource,
    private val pokeApiLocalRepository: PokeApiLocalRepository
) : ViewModel() {
    val selectedPokemon = MutableLiveData<Pokemon?>()
    val pokemonDao = database.pokemonDao()

    @OptIn(ExperimentalPagingApi::class)
    lateinit var flow: Flow<PagingData<PokemonItem>>

    @OptIn(ExperimentalPagingApi::class)
    fun initFlow(initIndex: Int? = null) {
        Log.d("INFO", "start init flow")
        flow = Pager(
            config = PagingConfig(pageSize = 30, initialLoadSize = 60, prefetchDistance = 0),
            remoteMediator = ExampleRemoteMediator(database, pokeApiDatasource, initIndex),
            pagingSourceFactory = {
                pokemonDao.pagingSource()
            }
        ).flow
            .map { pagingData ->
                pagingData.map {
                    PokemonItem(it.id, it.name, it.sprite, false, false, false)
                }
            }
        Log.d("INFO", "end init flow")
    }

    fun getPokemonList() {
//            getPokemonListRemote.invoke()?.let {
//                Log.d("INFO", "got ${it}")
//                pokemonState.postValue(it)
//            }
        Log.d("INFO", "got ${flow}")
    }

    fun selectPokemon(id: Int) {
        viewModelScope.launch {
            val pokemon = pokeApiLocalRepository.getPokemon(id)
            Log.d("INFO", "GOt pokemon from db $pokemon")
            selectedPokemon.postValue(pokemon)
        }
    }

    fun resetSelectedPokemon() {
        selectedPokemon.value = null
    }

    fun sortPokemon(
        byAttack: Boolean,
        byHp: Boolean,
        byDefense: Boolean
    ): MutableLiveData<List<PokemonItem>> {
        val result = MutableLiveData<List<PokemonItem>>()
        val selectors = ArrayList<Function1<Pokemon, Comparable<*>?>>()
        if (byAttack) {
            selectors.add(attackSelector)
        }
        if (byDefense) {
            selectors.add(defenseSelector)
        }
        if (byHp) {
            selectors.add(hpSelector)
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                var values = pokemonDao.getAll().map { it.toPokemon() }.toList()
                if (selectors.isNotEmpty()) {
                    values =
                        values.sortedWith(compareBy(*selectors.toTypedArray())).reversed()
                    Log.d("INFO", "sorted ${values.size} values")
                }
                var maxAttack = 0
                var maxDefence = 0
                var maxHp = 0
                for(pokemon in values){
                    maxAttack = max(maxAttack, pokemon.attack)
                    maxDefence = max(maxDefence, pokemon.defense)
                    maxHp = max(maxHp, pokemon.hp)
                }
                result.postValue(values.map { pokemon -> PokemonItem(pokemon.id, pokemon.name, pokemon.sprite, pokemon.attack == maxAttack && byAttack, pokemon.defense == maxDefence && byDefense, pokemon.hp == maxHp && byHp)})
            }
        }
        return result
    }

    companion object {
        private val attackSelector: Function1<Pokemon, Comparable<*>?> =
            { pokemon-> pokemon.attack }
        private val hpSelector: Function1<Pokemon, Comparable<*>?> =
            { pokemon-> pokemon.hp }
        private val defenseSelector: Function1<Pokemon, Comparable<*>?> =
            { pokemon-> pokemon.defense }
    }
}