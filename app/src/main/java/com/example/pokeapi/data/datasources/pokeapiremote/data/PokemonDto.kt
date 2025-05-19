package com.example.pokeapi.data.datasources.pokeapiremote.data

/**
 * Dto class for providing pokemon and order of pokemon on server to PagingLibrary.
 */
data class PokemonDto(
    val pokemonInfo: PokemonInfo, val order: Int
)