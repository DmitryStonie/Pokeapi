package com.example.pokeapi.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pokeapi.R

class PokemonFragment: Fragment(R.layout.fragment_pokemon_info) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.name).text = savedInstanceState?.getString("name")
    }
    companion object{
        const val POKEMON_FRAGMENT="PokemonFragment"
    }
}