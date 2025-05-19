package com.example.pokeapi.data.datasources.pokeapiremote.data

/**
 * Dto class for deserializing PokeApi list of pokemon response.
 */
data class PokemonList(
    val count: Int, val next: String, val prev: String, val results: List<NamedApiResource>
)