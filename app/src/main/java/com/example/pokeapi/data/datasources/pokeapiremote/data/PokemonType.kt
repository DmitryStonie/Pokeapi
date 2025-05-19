package com.example.pokeapi.data.datasources.pokeapiremote.data

/**
 * Dto class for deserializing PokeApi response, pokemon type object.
 */
data class PokemonType(
    val slot: Int, val type: NamedApiResource
)