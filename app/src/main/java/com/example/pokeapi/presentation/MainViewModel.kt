package com.example.pokeapi.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.map
import com.example.pokeapi.domain.entities.Pokemon
import com.example.pokeapi.domain.usecases.GetAllLocalPokemon
import com.example.pokeapi.domain.usecases.GetLocalPokemon
import com.example.pokeapi.domain.usecases.GetPokemonPages
import com.example.pokeapi.presentation.recyclerview.PokemonItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.max

/**
 * ViewModel for loading pokemon to recycler via PagingLibrary and via Room database. Also, it loads pokemon to PokemonFragment.
 * @param getPokemonPages Use case to get PagingData of pokemon.
 * @param getLocalPokemon Use case to get pokemon from local database.
 * @param getAllLocalPokemon Use case to get List of pokemon from local database.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPokemonPages: GetPokemonPages,
    private val getLocalPokemon: GetLocalPokemon,
    private val getAllLocalPokemon: GetAllLocalPokemon,
) : ViewModel() {
    val selectedPokemon = MutableLiveData<Pokemon?>()

    @OptIn(ExperimentalPagingApi::class)
    lateinit var flow: Flow<PagingData<PokemonItem>>

    /**
     * Creates new PagingLibrary flow with pokemon data.
     */
    @OptIn(ExperimentalPagingApi::class)
    fun initPagerFlow(initIndex: Int? = null) {
        viewModelScope.launch {
            flow = getPokemonPages.invoke(initIndex).map { pagingData ->
                pagingData.map { pokemon ->
                    PokemonItem(
                        pokemon.id, pokemon.name, pokemon.spriteUrl, false, false, false
                    )
                }
            }
        }
    }

    /**
     * Loads by id selected pokemon.
     */
    fun selectPokemon(id: Int) {
        viewModelScope.launch {
            val pokemon = getLocalPokemon.invoke(id)
            selectedPokemon.postValue(pokemon)
        }
    }
    /**
     * Resets selected pokemon.
     */
    fun resetSelectedPokemon() {
        selectedPokemon.value = null
    }

    /**
     * Gets all pokemon from local database and sorts it by attack, defense, hp. Attack has highest priority, hp - lowest. I.e. if selected byAttack and byDefense, pokemon with attack = 100 defence = 10 is higher than pokemon with attack = 90 defence = 200.
     * @param byAttack is sorting by attack.
     * @param byDefense is sorting defense.
     * @param byHp is sorting by hp.
     */
    fun sortPokemon(
        byAttack: Boolean, byDefense: Boolean, byHp: Boolean
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
            withContext(Dispatchers.IO) {
                var values = getAllLocalPokemon.invoke()
                if (selectors.isNotEmpty()) {
                    values = values.sortedWith(compareBy(*selectors.toTypedArray())).reversed()
                }
                var maxAttack = 0
                var maxDefence = 0
                var maxHp = 0
                for (pokemon in values) {
                    maxAttack = max(maxAttack, pokemon.attack)
                    maxDefence = max(maxDefence, pokemon.defense)
                    maxHp = max(maxHp, pokemon.hp)
                }
                result.postValue(values.map { pokemon ->
                    PokemonItem(
                        pokemon.id,
                        pokemon.name,
                        pokemon.spriteUrl,
                        pokemon.attack == maxAttack && byAttack,
                        pokemon.defense == maxDefence && byDefense,
                        pokemon.hp == maxHp && byHp
                    )
                })
            }
        }
        return result
    }

    companion object {
        private val attackSelector: Function1<Pokemon, Comparable<*>?> =
            { pokemon -> pokemon.attack }
        private val hpSelector: Function1<Pokemon, Comparable<*>?> = { pokemon -> pokemon.hp }
        private val defenseSelector: Function1<Pokemon, Comparable<*>?> =
            { pokemon -> pokemon.defense }
    }
}