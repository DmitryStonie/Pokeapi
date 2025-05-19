package com.example.pokeapi.data.datasources.pokeapiremote.data

/**
 * Dto class for PokeApi response.
 */
data class PokemonInfo(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>,
    val sprites: PokemonSprites
)