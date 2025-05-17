package com.example.pokeapi.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapi.domain.entities.Pokemon
import com.example.pokeapi.domain.usecases.GetPokemonListRemote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getPokemonListRemote: GetPokemonListRemote): ViewModel() {
//    val pokemons = ArrayList<Pokemon>()

    fun getPokemonList(){
        viewModelScope.launch {
            getPokemonListRemote.invoke()?.let {
                Log.d("INFO", "got ${it}")
//                pokemons.addAll(it)
            }
        }
    }
}