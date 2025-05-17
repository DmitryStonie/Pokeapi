package com.example.pokeapi.data.datasources.pokeapiremote.data

data class PokemonInfo(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>,
    val sprites: PokemonSprites
)